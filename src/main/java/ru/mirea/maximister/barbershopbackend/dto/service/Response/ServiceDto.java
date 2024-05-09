package ru.mirea.maximister.barbershopbackend.dto.service.Response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Duration;

public record ServiceDto(
        @PositiveOrZero
        Long id,
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
