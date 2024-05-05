package ru.mirea.maximister.barbershopbackend.dto.barber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SetBarberBarbershopRequest(
        @NotBlank
        String name,
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number,
        @Email
        String barberEmail
) {
}
