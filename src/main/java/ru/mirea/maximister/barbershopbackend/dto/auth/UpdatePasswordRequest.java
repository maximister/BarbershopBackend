package ru.mirea.maximister.barbershopbackend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for updating user password operation")
public record UpdatePasswordRequest(
        @Email
        @Schema(description = "Email of a user who wants to change password", example = "user@user.com")
        String email,
        @NotBlank
        @Schema(description = "User's current password", example = "password")
        String oldPassword,
        @NotBlank
        @Schema(description = "User's new password", example = "newpassword")
        String newPassword
) {
}
