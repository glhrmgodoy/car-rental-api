package br.com.godoy.carRental.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String cpf,
        String email,
        String password,
        LocalDateTime createdAt
) {
}
