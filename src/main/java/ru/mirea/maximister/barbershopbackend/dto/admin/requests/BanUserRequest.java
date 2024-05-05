package ru.mirea.maximister.barbershopbackend.dto.admin.requests;

import jakarta.validation.constraints.Email;

public record BanUserRequest(
        @Email
        String userEmail,
        String reason
) {
}
