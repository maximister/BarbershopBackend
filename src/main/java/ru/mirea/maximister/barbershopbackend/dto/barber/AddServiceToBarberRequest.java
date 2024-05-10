package ru.mirea.maximister.barbershopbackend.dto.barber;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dto for adding service for barber operation")
public record AddServiceToBarberRequest(
        @NotNull
        @Schema(description = "Name of service, which can be done in barber's barbershop",
        example = "Men's haircut")
        String serviceName
) {
}
