package ru.mirea.maximister.barbershopbackend.dto.reservation.responses;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopDto;
import ru.mirea.maximister.barbershopbackend.dto.service.Response.ServiceDto;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

import java.time.LocalDate;
import java.time.OffsetTime;

public record ReservationWithoutClientInfoDto(
        @NotNull
        LocalDate date,
        @NotNull
        OffsetTime time,
        @NotNull
        ServiceDto service,
        @NotNull
        UserDto barber,
        @NotNull
        BarbershopDto barbershop
) {
}
