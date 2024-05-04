package ru.mirea.maximister.barbershopbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record SignInRequest(
        @Email
        String email,
        @NotBlank
        String password
) {
}
