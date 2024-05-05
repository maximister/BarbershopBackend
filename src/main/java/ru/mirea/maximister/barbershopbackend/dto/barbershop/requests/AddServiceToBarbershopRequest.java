package ru.mirea.maximister.barbershopbackend.dto.barbershop.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddServiceToBarbershopRequest(
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String number,
        @NotNull
        String serviceName
) {
}
