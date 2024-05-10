package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetTime;

@Schema(description = "Dto with Barbershop information")
public record BarbershopDto(
        @Schema(description = "Barbershop's id", example = "611")
        Long id,
        @Schema(description = "Barbershop's name", example = "Men's Choice")
        String name,
        @Schema(description = "Barbershop's description", example = "Best barbershop in the city")
        String description,
        @Schema(description = "Barbershop's city", example = "Moscow")
        String city,
        @Schema(description = "Barbershop's postal code", example = "611422")
        String postalCode,
        @Schema(description = "Barbershop's street", example = "Arbat street")
        String street,
        @Schema(description = "Barbershop's building number", example = "1")
        String number,
        @Schema(description = "Barbershop's longitude coordinate", example = "11.22")
        double longitude,
        @Schema(description = "Barbershop's latitude coordinate", example = "-0.77")
        double latitude,
        @Schema(description = "Barbershop's open time", example = "08:00Z")
        OffsetTime openTime,
        @Schema(description = "Barbershop's close time", example = "22:00Z")
        OffsetTime closeTime
) {
}
