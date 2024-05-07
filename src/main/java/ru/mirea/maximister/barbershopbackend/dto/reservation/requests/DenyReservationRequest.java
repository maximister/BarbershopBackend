package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetTime;

public record DenyReservationRequest(
        @NotNull
        LocalDate date,
        @NotNull
        OffsetTime time,
        @Email
        String barberEmail
) {
}
