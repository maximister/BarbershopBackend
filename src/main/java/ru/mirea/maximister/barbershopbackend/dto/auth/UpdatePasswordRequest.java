package ru.mirea.maximister.barbershopbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
        @Email
        String email,
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword
) {
}
