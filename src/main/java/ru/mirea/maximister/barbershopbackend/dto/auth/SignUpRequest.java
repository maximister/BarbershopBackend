package ru.mirea.maximister.barbershopbackend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dto for sign up operation")
public record SignUpRequest(
        @Email
        @Schema(description = "Email of a user who tries to sign un", example = "user@user.com")
        String email,
        @NotBlank
        @Schema(description = "User's password", example = "password")
        String password,
        @NotBlank
        @Schema(description = "User's phone number", example = "89997772211")
        String phoneNumber, //TODO: написать аннотацию для валидации телефона
        @NotBlank
        @Schema(description = "User's fullname", example = "Sergey Dmitrievich Ferz")
        String fullname
) {
}
