package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "Dto with request to get all barber's free time slots")
public record GetBarberSlotsRequest(
        @Email
        @Schema(description = "Email of requested barber", example = "barber@barber.com")
        String email
) {
}
