package ru.mirea.maximister.barbershopbackend.dto.reservation.responses;

import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopDto;
import ru.mirea.maximister.barbershopbackend.dto.reservation.FreeBarbersSlots;

import java.util.List;

public record SlotsInBarbershopResponse(
        @NotNull
        BarbershopDto barbershop,
        List<FreeBarbersSlots> slots
) {
}
