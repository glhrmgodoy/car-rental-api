package br.com.godoy.carRental.service;

import br.com.godoy.carRental.dto.request.RentalRequest;
import br.com.godoy.carRental.dto.response.RentalResponse;
import br.com.godoy.carRental.exception.BusinessException;
import br.com.godoy.carRental.exception.NotFoundException;
import br.com.godoy.carRental.mapper.RentalMapper;
import br.com.godoy.carRental.model.entity.Car;
import br.com.godoy.carRental.model.entity.Rental;
import br.com.godoy.carRental.model.entity.User;
import br.com.godoy.carRental.model.enums.CarStatus;
import br.com.godoy.carRental.model.enums.RentalStatus;
import br.com.godoy.carRental.repository.CarRepository;
import br.com.godoy.carRental.repository.RentalRepository;
import br.com.godoy.carRental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RentalMapper rentalMapper;

    public RentalResponse create(RentalRequest request) {
        if (request.endDate().isBefore(request.startDate()) ||
                request.endDate().isEqual(request.startDate())) {
            throw new BusinessException("Data do fim deve ser maior que a data de início");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (rentalRepository.existsByUserIdAndStatus(request.userId(), RentalStatus.ACTIVE)) {
            throw new BusinessException("Usuário já possui uma locação ativa");
        }

        Car car = carRepository.findById(request.carId())
                .orElseThrow(() -> new NotFoundException("Carro não encontrado"));

        if (!car.getStatus().equals(CarStatus.AVAILABLE)) {
            throw new BusinessException("Carro não está disponível para locação");
        }

        long days = ChronoUnit.DAYS.between(request.startDate(), request.endDate());
        BigDecimal totalAmount = car.getDailyRate().multiply(BigDecimal.valueOf(days));

        Rental rental = rentalMapper.toEntity(request);
        rental.setUser(user);
        rental.setCar(car);
        rental.setTotalAmount(totalAmount);
        rental.setStatus(RentalStatus.ACTIVE);

        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        Rental saved = rentalRepository.save(rental);
        return rentalMapper.toResponse(saved);
    }

    public List<RentalResponse> findAll() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toResponse)
                .toList();
    }

    public RentalResponse findById(UUID id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locação não encontrada"));
        return rentalMapper.toResponse(rental);
    }

    public List<RentalResponse> findAllByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return rentalRepository.findAllByUserId(userId)
                .stream()
                .map(rentalMapper::toResponse)
                .toList();
    }

    public RentalResponse returnCar(UUID id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locação não encontrada"));

        if (!rental.getStatus().equals(RentalStatus.ACTIVE)) {
            throw new BusinessException("Apenas locações ativas podem ser devolvidas");
        }

        LocalDate returnDate = LocalDate.now();
        BigDecimal totalAmount = rental.getTotalAmount();

        if (returnDate.isAfter(rental.getEndDate())) {
            long extraDays = ChronoUnit.DAYS.between(rental.getEndDate(), returnDate);
            BigDecimal dailyRate = rental.getCar().getDailyRate();
            BigDecimal fine = dailyRate.multiply(BigDecimal.valueOf(extraDays))
                    .multiply(BigDecimal.valueOf(1.20));
            totalAmount = totalAmount.add(fine);
        }

        rental.setReturnDate(returnDate);
        rental.setTotalAmount(totalAmount);
        rental.setStatus(RentalStatus.COMPLETED);

        rental.getCar().setStatus(CarStatus.AVAILABLE);
        carRepository.save(rental.getCar());

        Rental update = rentalRepository.save(rental);
        return rentalMapper.toResponse(update);
    }

    public void cancel(UUID id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Locação não encontrada"));

        if (!rental.getStatus().equals(RentalStatus.ACTIVE)) {
            throw new BusinessException("Apenas locações ativas podem ser cancelada");
        }

        long hoursUntilStart = ChronoUnit.HOURS.between(LocalDate.now().atStartOfDay(),
                rental.getStartDate().atStartOfDay());

        if (hoursUntilStart < 24) {
            BigDecimal fine = rental.getCar().getDailyRate();
            rental.setTotalAmount(fine);
        } else {
            rental.setTotalAmount(BigDecimal.ZERO);
        }

        rental.setStatus(RentalStatus.CANCELLED);
        rental.getCar().setStatus(CarStatus.AVAILABLE);
        carRepository.save(rental.getCar());

        rentalRepository.save(rental);
    }
}
