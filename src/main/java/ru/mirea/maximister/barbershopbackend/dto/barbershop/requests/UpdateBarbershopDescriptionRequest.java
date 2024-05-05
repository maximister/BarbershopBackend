package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateBarbershopDescriptionRequest(
        @NotBlank
        String name,
        @NotBlank
        String newDescription,
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number
) {
}