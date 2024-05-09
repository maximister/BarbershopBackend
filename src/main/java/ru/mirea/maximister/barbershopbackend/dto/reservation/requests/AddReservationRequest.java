package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetTime;

public record AddReservationRequest(
        @NotNull
        LocalDate date,
        @NotNull
        OffsetTime time,
        @NotBlank
        String serviceName,
        @Email
        String barberEmail
) {
}
