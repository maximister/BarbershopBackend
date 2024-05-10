package ru.mirea.maximister.barbershopbackend.dto.admin.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "Dto for revoking user role operation")
public record RevokeRoleRequest(
        @Email
        @Schema(description = "Email of a user who needs to be revoked", example = "user@user.com")
        String email,
        @NotBlank
        @Schema(description = "Revoked role", example = "ROLE_ADMIN")
        ru.mirea.maximister.barbershopbackend.domain.enums.Role role
        //TODO: добавить аннотацию для валидации ролей
) {
}
