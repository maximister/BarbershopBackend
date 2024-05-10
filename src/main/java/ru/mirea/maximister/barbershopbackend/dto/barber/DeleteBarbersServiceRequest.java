package ru.mirea.maximister.barbershopbackend.dto.barber;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dto for deleting service for barber operation")
public record DeleteBarbersServiceRequest(
        @NotNull
        @Schema(description = "Name of service, which needs to be removed from barber's list",
                example = "Men's haircut")
        String serviceName
) {
}
