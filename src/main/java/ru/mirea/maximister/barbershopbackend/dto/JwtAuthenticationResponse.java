package ru.mirea.maximister.barbershopbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dto with user's JWT Authorization token")
public record JwtAuthenticationResponse(
        @NotNull
        @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBtYWlsLmNvbSIsImlhdCI6MTcxNTExNDA2OCwiZXhwIjoxNzE1MTE3NjY4fQ.Gc3hs9dtPvZXdanaJqnThWFbZjqJusKAh2-pIUX2_jQ")
        String token
) {
}