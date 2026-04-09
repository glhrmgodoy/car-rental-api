package br.com.godoy.carRental.dto.response;

import br.com.godoy.carRental.model.enums.CategoryName;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        CategoryName name,
        String description,
        LocalDateTime createdAt
) {
}
