package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import jakarta.validation.constraints.NotBlank;

public record GetAllBarbershopBarbersRequest(
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number
) {
}
