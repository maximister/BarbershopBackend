package ru.mirea.maximister.barbershopbackend.dto.service.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

@Schema(description = "Dto with request to add service")
public record AddServiceRequest(
        @NotBlank
        @Schema(description = "Name of new service", example = "Men's Haircut")
        String name,
        @NotBlank
        @Schema(description = "Description of new service", example = "Trimming hair")
        String description,
        @Positive
        @Schema(description = "Price of new service", example = "1500")
        Integer price,
        @NotNull
        @Schema(description = "Duration of new service", example = "PT45M")
        Duration duration
) {
}
