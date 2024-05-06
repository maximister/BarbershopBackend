package ru.mirea.maximister.barbershopbackend.dto.reservation.responses;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.BarberResponse;

import java.util.List;

public record BarberReservationsList(
        @NotNull
        BarberResponse barber,
        List<ReservationWithoutBarberInfoDto> reservations
) {
}
