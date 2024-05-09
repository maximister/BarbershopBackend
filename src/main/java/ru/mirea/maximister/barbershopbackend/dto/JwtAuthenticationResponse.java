package ru.mirea.maximister.barbershopbackend.dto;

import jakarta.validation.constraints.NotNull;

public record JwtAuthenticationResponse(
        @NotNull
        String token
) {
}