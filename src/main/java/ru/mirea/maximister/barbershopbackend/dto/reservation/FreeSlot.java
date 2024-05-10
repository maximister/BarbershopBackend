package ru.mirea.maximister.barbershopbackend.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetTime;

@Schema(description = "Dto with barber's free time slot")
public record FreeSlot(
        @NotNull
        @Schema(description = "Date of time slot", example = "2024-05-13")
        LocalDate date,
        @NotNull
        @Schema(description = "Time of time slot", example = "12:15Z")
        OffsetTime time
) {
}
