package br.com.godoy.carRental.controller;

import br.com.godoy.carRental.dto.request.CarRequest;
import br.com.godoy.carRental.dto.response.CarResponse;
import br.com.godoy.carRental.model.enums.CarStatus;
import br.com.godoy.carRental.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponse> create(@RequestBody CarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/{available}")
    public ResponseEntity<List<CarResponse>> findAllAvailable() {
        return ResponseEntity.ok(carService.findAllAvailable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> update(@PathVariable UUID id,
                                              @RequestBody @Valid CarRequest request) {
        return ResponseEntity.ok(carService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id,
                                             @RequestParam CarStatus status) {
        carService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
