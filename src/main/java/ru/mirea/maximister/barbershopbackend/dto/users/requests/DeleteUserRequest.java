package ru.mirea.maximister.barbershopbackend.dto.users.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;


@Schema(description = "Dto with request to delete user")
public record DeleteUserRequest(
        @Email
        @Schema(description = "Email of required user", example = "user@user.com")
        String email
) {
}
