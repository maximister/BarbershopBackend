package ru.mirea.maximister.barbershopbackend.dto.reservation;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.BarberResponse;

import java.util.List;

public record FreeBarbersSlots(
        @NotNull
        BarberResponse barber,
        List<FreeSlot> slots
) {
}
