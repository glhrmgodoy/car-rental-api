package br.com.godoy.carRental.dto.request;

import br.com.godoy.carRental.model.enums.CarStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CarRequest(
        @NotBlank(message = "Placa é obrigatória")
        String licensePlate,

        @NotBlank(message = "Marca é obrigatória")
        String brand,

        @NotBlank(message = "Modelo é obrigatória")
        String model,

        @NotNull(message = "Ano é obrigatório")
        @Min(value = 1990, message = "Ano inválido")
        @Max(value = 2027, message = "Ano inválido")
        Integer year,

        @NotNull(message = "Valor da diária é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor da diária deve ser maior que zero")
        BigDecimal dailyRate,

        @NotNull(message = "Status é obrigatório")
        CarStatus status,

        @NotNull(message = "ID da categoria é obrigatório")
        UUID categoryId
) {
}
