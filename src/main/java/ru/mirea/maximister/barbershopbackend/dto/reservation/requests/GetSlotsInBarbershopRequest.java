package ru.mirea.maximister.barbershopbackend.dto.reservation.requests;

import jakarta.validation.constraints.NotBlank;

public record GetSlotsInBarbershopRequest(
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number
) {
}
