package ru.mirea.maximister.barbershopbackend.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RevokeRoleRequest(
        @Email
        String email,
        @NotBlank
        String Role //TODO: добавить аннотацию для валидации ролей
) {
}
