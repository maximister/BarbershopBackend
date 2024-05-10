package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetTime;

@Schema(description = "Dto for change barbershop's work time operation")
public record SetBarbershopWorkTimeRequest(
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
        String number,
        @NotNull
        @Schema(description = "Barbershop's open time", example = "08:00Z")
        OffsetTime openTime,
        @NotNull
        @Schema(description = "Barbershop's close time", example = "22:00Z")
        OffsetTime closeTime
) {
}
