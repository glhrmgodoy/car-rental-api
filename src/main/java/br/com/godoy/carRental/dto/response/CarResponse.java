package br.com.godoy.carRental.dto.response;

import br.com.godoy.carRental.model.enums.CarStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CarResponse(
        UUID id,
        String licensePlate,
        String brand,
        String model,
        Integer year,
        BigDecimal dailyRate,
        CarStatus status,
        String categoryName,
        LocalDateTime createdAt
) {
}
