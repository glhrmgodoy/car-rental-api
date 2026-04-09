package br.com.godoy.carRental.service;

import br.com.godoy.carRental.dto.request.CarRequest;
import br.com.godoy.carRental.dto.response.CarResponse;
import br.com.godoy.carRental.exception.BusinessException;
import br.com.godoy.carRental.exception.NotFoundException;
import br.com.godoy.carRental.mapper.CarMapper;
import br.com.godoy.carRental.model.entity.Car;
import br.com.godoy.carRental.model.entity.Category;
import br.com.godoy.carRental.model.enums.CarStatus;
import br.com.godoy.carRental.repository.CarRepository;
import br.com.godoy.carRental.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CarMapper carMapper;

    public CarResponse create(CarRequest request) {
        if (carRepository.existsByLicensePlate(request.licensePlate())) {
            throw new BusinessException("Placa já cadastrada");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        Car car = carMapper.toEntity(request);
        car.setCategory(category);

        Car saved = carRepository.save(car);
        return carMapper.toResponse(saved);
    }

    public List<CarResponse> findAll() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toResponse)
                .toList();
    }

    public List<CarResponse> findAllAvailable() {
        return carRepository.findAllByStatus(CarStatus.AVAILABLE)
                .stream()
                .map(carMapper::toResponse)
                .toList();
    }

    public CarResponse findById(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return carMapper.toResponse(car);
    }

    public CarResponse update(UUID id, CarRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!car.getLicensePlate().equals(request.licensePlate()) &&
                carRepository.existsByLicensePlate(request.licensePlate())) {
            throw new BusinessException("Placa já cadastrada");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        car.setLicensePlate(request.licensePlate());
        car.setBrand(request.brand());
        car.setModel(request.model());
        car.setYear(request.year());
        car.setDailyRate(request.dailyRate());
        car.setStatus(request.status());
        car.setCategory(category);

        Car update = carRepository.save(car);

        return carMapper.toResponse(update);
    }

    public void updateStatus(UUID id, CarStatus status) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carro não encontrado"));

        if (car.getStatus().equals(CarStatus.RENTED) && status.equals(CarStatus.AVAILABLE)) {
            throw new BusinessException("Carro locado não pode ser diretamente marcado como disponível, realize a devolução");
        }

        car.setStatus(status);
        carRepository.save(car);
    }

    public void delete(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carro não encontrado"));

        if (car.getStatus().equals(CarStatus.RENTED)) {
            throw new BusinessException("Carro locado não pode ser removido");
        }

        car.setActive(false);
        carRepository.save(car);
    }
}
