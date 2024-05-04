package ru.mirea.maximister.barbershopbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String phoneNumber, //TODO: написать аннотацию для валидации телефона
        @NotBlank
        String fullname
) {
}
