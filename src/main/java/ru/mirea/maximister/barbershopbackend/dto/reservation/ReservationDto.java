package ru.mirea.maximister.barbershopbackend.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopDto;
import ru.mirea.maximister.barbershopbackend.dto.service.Response.ServiceDto;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

import java.time.LocalDate;
import java.time.OffsetTime;

@Schema(description = "Dto with reservation info")
public record ReservationDto(
        @NotNull
        @Schema(description = "Date of reservation", example = "2024-05-13")
        LocalDate date,
        @NotNull
        @Schema(description = "Time of reservation", example = "12:15Z")
        OffsetTime time,
        @NotNull
        @Schema(description = "Information about reserved service")
        ServiceDto service,
        @NotNull
        @Schema(description = "Information about reserved barber")
        UserDto barber,
        @NotNull
        @Schema(description = "Information about client (reservation owner)")
        UserDto client,
        @NotNull
        @Schema(description = "Information reserved barbershop")
        BarbershopDto barbershop
) {
}
