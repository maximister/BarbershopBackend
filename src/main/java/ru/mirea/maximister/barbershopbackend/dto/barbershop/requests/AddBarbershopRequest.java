package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import jakarta.validation.constraints.NotBlank;

public record AddBarbershopRequest(
        @NotBlank
        String name,
        String description,
        @NotBlank
        String city,
        @NotBlank
        String postalCode,
        @NotBlank
        String street,
        @NotBlank
        String number,
        double longitude,
        double latitude
) {
}
