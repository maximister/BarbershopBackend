package ru.mirea.maximister.barbershopbackend.dto.admin.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "Dto for add new role to user operation")
public record AddRoleRequest(
        @Email
        @Schema(description = "Email of a user who needs new role", example = "user@user.com")
        String email
) {
}
