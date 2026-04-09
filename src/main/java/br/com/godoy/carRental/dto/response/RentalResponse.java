package br.com.godoy.carRental.dto.response;

import br.com.godoy.carRental.model.enums.RentalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RentalResponse(
        UUID id,
        UUID userId,
        String userName,
        UUID carId,
        String carBrand,
        String carModel,
        LocalDate startDate,
        LocalDate endDate,
        LocalDate returnDate,
        BigDecimal totalAmount,
        RentalStatus status,
        LocalDateTime createdAt
) {
}
