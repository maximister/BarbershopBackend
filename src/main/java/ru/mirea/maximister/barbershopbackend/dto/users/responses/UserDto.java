package ru.mirea.maximister.barbershopbackend.dto.users.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto with information about user")
public record UserDto(
        @Schema(description = "User's id", example = "14")
        Long id,
        @Schema(description = "User's email", example = "user@user.com")
        String email,
        @Schema(description = "User's phone number", example = "89998887755")
        String phoneNumber,
        @Schema(description = "User's fullname", example = "Sergey Dmitrievich Ferz")
        String fullname
) {
}
