package ru.mirea.maximister.barbershopbackend.dto.admin.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
@Schema(description = "Dto for banning user operation")
public record BanUserRequest(
        @Email
        @Schema(description = "Email of a user who needs to be banned", example = "user@user.com")
        String email,
        @Schema(description = "The reason of a ban", nullable = true,
                example = "Rude violation of community rules")
        String reason
) {
}
