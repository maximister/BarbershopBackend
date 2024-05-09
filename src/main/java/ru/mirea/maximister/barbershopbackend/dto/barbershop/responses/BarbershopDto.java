package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

import java.time.OffsetTime;

public record BarbershopDto(
        Long id,
        String name,
        String description,
        String city,
        String postalCode,
        String street,
        String number,
        double longitude,
        double latitude,
        OffsetTime openTime,
        OffsetTime closeTime
) {
}
