package br.com.godoy.carRental.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RentalRequest(
        @NotNull(message = "ID do usuário é obrigatório")
        UUID userId,

        @NotNull(message = "ID do carro é obrigatório")
        UUID carId,

        @NotNull(message = "Data de início é obrigatória")
        @Future(message = "Data de início deve ser uma data futura")
        LocalDate startDate,

        @NotNull(message = "Data de fim é obrigatória")
        @Future(message = "Data de fim deve ser uma data futura")
        LocalDate endDate
) {
}
