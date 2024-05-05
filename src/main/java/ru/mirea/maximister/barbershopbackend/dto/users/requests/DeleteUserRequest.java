package ru.mirea.maximister.barbershopbackend.dto.users.requests;

import jakarta.validation.constraints.Email;

public record DeleteUserRequest(
        @Email
        String email
) {
}
