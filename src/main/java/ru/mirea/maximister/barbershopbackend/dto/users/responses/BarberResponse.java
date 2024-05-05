package ru.mirea.maximister.barbershopbackend.dto.users.responses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BarberResponse(
        @Email
        String email,
        @NotBlank
        String fullname
) {
}
