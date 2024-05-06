package ru.mirea.maximister.barbershopbackend.dto.reservation.responses;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.ClientResponse;

import java.util.List;

public record ClientReservationList(
        @NotNull
        ClientResponse client,
        List<ReservationWithoutClientInfoDto> reservations
) {
}
