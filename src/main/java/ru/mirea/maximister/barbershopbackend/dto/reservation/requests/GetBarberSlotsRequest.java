package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import jakarta.validation.constraints.Email;

public record GetBarberSlotsRequest(
        @Email
        String email
) {
}
