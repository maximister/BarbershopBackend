package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import jakarta.validation.constraints.NotBlank;

public record DeleteBarbershopServiceRequest(
        @NotBlank
        String serviceName,
        @NotBlank
        String barberShopName,
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number
) {
}
