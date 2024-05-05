package ru.mirea.maximister.barbershopbackend.dto.service.Response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record ServiceResponse(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @Positive
        Integer price,
        @NotNull
        Duration duration
) {
}
