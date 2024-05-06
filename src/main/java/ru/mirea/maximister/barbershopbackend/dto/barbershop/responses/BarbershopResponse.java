package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

public record BarbershopResponse(
        String name,
        String description,
        String city,
        String postalCode,
        String street,
        String number,
        double longitude,
        double latitude
) {
}
