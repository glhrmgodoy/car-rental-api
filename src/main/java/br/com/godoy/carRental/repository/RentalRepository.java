package br.com.godoy.carRental.repository;

import br.com.godoy.carRental.model.entity.Rental;
import br.com.godoy.carRental.model.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

    List<Rental> findAllByUserId(UUID userId);
    List<Rental> findAllByCarId(UUID carId);

    boolean existsByUserIdAndStatus(UUID userId, RentalStatus status);
}
