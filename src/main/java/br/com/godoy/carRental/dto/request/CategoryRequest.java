package br.com.godoy.carRental.dto.request;

import br.com.godoy.carRental.model.enums.CategoryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "Nome da categoria é obrigatório")
        CategoryName name,

        @NotBlank(message = "Descrição é obrigatória")
        String description
) {
}
