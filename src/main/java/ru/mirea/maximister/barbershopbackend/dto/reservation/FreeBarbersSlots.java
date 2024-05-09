package ru.mirea.maximister.barbershopbackend.dto.reservation;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

import java.util.List;

public record FreeBarbersSlots(
        @NotNull
        UserDto barber,
        List<FreeSlot> slots
) {
}
