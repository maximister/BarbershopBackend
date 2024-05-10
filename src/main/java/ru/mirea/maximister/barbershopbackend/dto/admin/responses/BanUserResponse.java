package ru.mirea.maximister.barbershopbackend.dto.admin.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto for revoking user role operation response")
public record BanUserResponse(
        @Schema(description = "Email of a user who was banned", example = "user@user.com")
        String email,
        @Schema(description = "The reason of a ban", nullable = true,
                example = "Rude violation of community rules")
        String reason
) {
}
