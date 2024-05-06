package ru.mirea.maximister.barbershopbackend.dto.reservation;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopResponse;
import ru.mirea.maximister.barbershopbackend.dto.service.Response.ServiceResponse;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.BarberResponse;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.ClientResponse;

import java.time.LocalDate;
import java.time.OffsetTime;

public record ReservationDto(
        @NotNull
        LocalDate date,
        @NotNull
        OffsetTime time,
        @NotNull
        ServiceResponse service,
        @NotNull
        BarberResponse barber,
        @NotNull
        ClientResponse client,
        @NotNull
        BarbershopResponse barbershop
) {
}
