package br.com.godoy.carRental.repository;

import br.com.godoy.carRental.model.entity.Car;
import br.com.godoy.carRental.model.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    Optional<Car> findByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);

    List<Car> findAllByStatus(CarStatus status);
}
