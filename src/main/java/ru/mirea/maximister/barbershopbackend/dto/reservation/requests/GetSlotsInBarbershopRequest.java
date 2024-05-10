package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto with request to get all free time slots in barbershop")
public record GetSlotsInBarbershopRequest(
        @NotBlank
        @Schema(description = "Barbershop's city", example = "Moscow")
        String city,
        @NotBlank
        @Schema(description = "Barbershop's street", example = "Lenin street")
        String street,
        @NotBlank
        @Schema(description = "Barbershop's house number", example = "4")
        String number
) {
}
