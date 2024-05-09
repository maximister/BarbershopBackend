package ru.mirea.maximister.barbershopbackend.dto.service.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record DeleteServiceRequest(
        @NotBlank
        String name
) {
}
