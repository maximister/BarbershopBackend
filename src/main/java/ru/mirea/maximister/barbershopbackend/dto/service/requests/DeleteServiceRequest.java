package ru.mirea.maximister.barbershopbackend.dto.service.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

@Schema(description = "Dto with request to delete service")
public record DeleteServiceRequest(
        @NotBlank
        @Schema(description = "Name of deleting service", example = "Men's Haircut")
        String name
) {
}
