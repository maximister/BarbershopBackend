package ru.mirea.maximister.barbershopbackend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Schema(description = "Dto for sign in operation")
public record SignInRequest(
        @Email
        @Schema(description = "Email of a user who tries to sign in", example = "user@user.com")
        String email,
        @NotBlank
        @Schema(description = "User's password", example = "password")
        String password
) {
}
