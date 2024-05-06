package ru.mirea.maximister.barbershopbackend.dto.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetTime;

public record FreeSlot(
        @NotNull
        LocalDate date,
        @NotNull
        OffsetTime time
) {
}
