package ru.mirea.maximister.barbershopbackend.dto.admin.requests;

import jakarta.validation.constraints.Email;

public record AddRoleRequest(
        @Email
        String email
) {
}
