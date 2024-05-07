package ru.mirea.maximister.barbershopbackend.dto.barber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DeleteBarbersServiceRequest(
        @NotNull
        String serviceName
) {
}
