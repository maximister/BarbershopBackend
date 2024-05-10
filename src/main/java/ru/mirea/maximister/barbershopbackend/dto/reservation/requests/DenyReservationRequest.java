package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetTime;

@Schema(description = "Dto with request to deny user's reservation")
public record DenyReservationRequest(
        @NotNull
        @Schema(description = "Date of reservation", example = "2024-05-13")
        LocalDate date,
        @NotNull
        @Schema(description = "Time of reservation", example = "12:15Z")
        OffsetTime time,
        @Email
        @Schema(description = "Email of reserved barber", example = "barber@barber.com")
        String barberEmail
) {
}
