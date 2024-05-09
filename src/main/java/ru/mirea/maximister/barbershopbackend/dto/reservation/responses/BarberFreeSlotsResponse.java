package ru.mirea.maximister.barbershopbackend.dto.reservation.responses;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.reservation.FreeSlot;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

import java.util.List;

public record BarberFreeSlotsResponse(
        @NotNull
        UserDto barber,
        List<FreeSlot> slots
) {
}
