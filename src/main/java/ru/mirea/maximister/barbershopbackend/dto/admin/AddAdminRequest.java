package ru.mirea.maximister.barbershopbackend.dto.admin;

import jakarta.validation.constraints.Email;

public record AddAdminRequest(
        @Email
        String email
) {
}
