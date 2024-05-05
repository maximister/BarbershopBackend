package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetTime;

public record SetBarbershopWorkTimeRequest(
        @NotBlank
        String name,
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number,
        @NotNull
        OffsetTime openTime,
        @NotNull
        OffsetTime closeTime
) {
}
