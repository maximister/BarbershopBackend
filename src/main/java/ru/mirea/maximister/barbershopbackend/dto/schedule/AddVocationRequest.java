package ru.mirea.maximister.barbershopbackend.dto.schedule;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetTime;

public record AddVocationRequest(
        @Positive
        @Max(7)
        Integer dayOfWeek,
        @NotNull
        OffsetTime from,
        @NotNull
        OffsetTime to
) {
}
