package br.com.godoy.carRental.controller;

import br.com.godoy.carRental.dto.request.RentalRequest;
import br.com.godoy.carRental.dto.response.RentalResponse;
import br.com.godoy.carRental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalResponse> create(@RequestBody @Valid RentalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> findAll() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RentalResponse>> findAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(rentalService.findAllByUserId(userId));
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<RentalResponse> returnCar(@PathVariable UUID id) {
        return ResponseEntity.ok(rentalService.returnCar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        rentalService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
