package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for removing service from a barbershop's list operation")
public record DeleteBarbershopServiceRequest(
        @NotBlank
        @Schema(description = "Existing service name", example = "Men's haircut")
        String serviceName,
        @NotBlank
        @Schema(description = "Barbershop's name", example = "Men's Choice")
        String barberShopName,
        @NotBlank
        @Schema(description = "Barbershop's city", example = "Moscow")
        String city,
        @NotBlank
        @Schema(description = "Barbershop's street", example = "Arbat street")
        String street,
        @NotBlank
        @Schema(description = "Existing service name", example = "Men's haircut")
        String number
) {
}
