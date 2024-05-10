package ru.mirea.maximister.barbershopbackend.dto.service.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Duration;

@Schema(description = "Dto with information about service")
public record ServiceDto(
        @PositiveOrZero
        @Schema(description = "Id of service", example = "143")
        Long id,
        @NotBlank
        @Schema(description = "Name of service", example = "Men's Haircut")
        String name,
        @NotBlank
        @Schema(description = "Description of service", example = "Trimming hair")
        String description,
        @Positive
        @Schema(description = "Price of service", example = "1500")
        Integer price,
        @NotNull
        @Schema(description = "Duration of service", example = "PT45M")
        Duration duration
) {
}
