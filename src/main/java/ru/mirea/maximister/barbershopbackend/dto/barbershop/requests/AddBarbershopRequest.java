package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for adding barbershop operation")
public record AddBarbershopRequest(
        @NotBlank
        @Schema(description = "Barbershop's name", example = "Men's Choice")
        String name,
        @NotBlank
        @Schema(description = "Barbershop's description", example = "Best barbershop in the city")
        String description,
        @NotBlank
        @Schema(description = "Barbershop's city", example = "Moscow")
        String city,
        @NotBlank
        @Schema(description = "Barbershop's postal code", example = "611422")
        String postalCode,
        @NotBlank
        @Schema(description = "Barbershop's street", example = "Arbat street")
        String street,
        @NotBlank
        @Schema(description = "Barbershop's building number", example = "1")
        String number,
        @Schema(description = "Barbershop's longitude coordinate", example = "11.22")
        double longitude,
        @Schema(description = "Barbershop's latitude coordinate", example = "-0.77")
        double latitude
) {
}
