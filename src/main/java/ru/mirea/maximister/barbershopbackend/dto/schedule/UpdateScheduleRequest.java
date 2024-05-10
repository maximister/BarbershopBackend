package ru.mirea.maximister.barbershopbackend.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetTime;

@Schema(description = "Dto with request to update barber's schedule")
public record UpdateScheduleRequest(
        @Positive
        @Max(7)
        @Schema(description = "Updated day of week", example = "5 (Friday)")
        Integer dayOfWeek,
        @NotNull
        @Schema(description = "Time of updated day start", example = "08:00Z")
        OffsetTime from,
        @NotNull
        @Schema(description = "Time of updated day end", example = "22:00Z")
        OffsetTime to
) {
}
