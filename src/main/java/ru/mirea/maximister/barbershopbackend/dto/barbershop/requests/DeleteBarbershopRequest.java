package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for removing barbershop operation")
public record DeleteBarbershopRequest(
        @NotBlank
        @Schema(description = "Barbershop's name", example = "Men's Choice")
        String name,
        @NotBlank
        @Schema(description = "Barbershop's city", example = "Moscow")
        String city,
        @NotBlank
        @Schema(description = "Barbershop's street", example = "Arbat street")
        String street,
        @NotBlank
        @Schema(description = "Barbershop's building number", example = "1")
        String number
) {
}
