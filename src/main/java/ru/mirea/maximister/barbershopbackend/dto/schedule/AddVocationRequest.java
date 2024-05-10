package ru.mirea.maximister.barbershopbackend.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetTime;

@Schema(description = "Dto with request to add vocation to a barber")
public record AddVocationRequest(
        @Positive
        @Max(7)
        @Schema(description = "Day of vocation's week", example = "5 (Friday)")
        Integer dayOfWeek,
        @NotNull
        @Schema(description = "Time of vocation's start", example = "08:00Z")
        OffsetTime from,
        @NotNull
        @Schema(description = "Time of vocation's end", example = "22:00Z")
        OffsetTime to
) {
}
