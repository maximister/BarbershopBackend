package ru.mirea.maximister.barbershopbackend.dto.admin;

import jakarta.validation.constraints.Email;

public record BanUserRequest(
        @Email
        String userEmail,
        String reason
) {
}
