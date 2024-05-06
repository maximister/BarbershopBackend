package ru.mirea.maximister.barbershopbackend.dto.reservation.responses;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopResponse;
import ru.mirea.maximister.barbershopbackend.dto.reservation.FreeBarbersSlots;

import java.util.List;

public record SlotsInBarbershopResponse(
        @NotNull
        BarbershopResponse barbershop,
        List<FreeBarbersSlots> slots
) {
}
