package ru.mirea.maximister.barbershopbackend.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetTime;

@Schema(description = "Dto with request to delete barber's schedule")
public record DeleteScheduleRequest(
        @Positive
        @Max(7)
        @Schema(description = "Day of schedule's week", example = "5 (Friday)")
        Integer dayOfWeek,
        @Schema(description = "Time of schedule's start", example = "08:00Z")
        OffsetTime from,
        @Schema(description = "Time of schedule's end", example = "22:00Z")
        OffsetTime to
) {
}
