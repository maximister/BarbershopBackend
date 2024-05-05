package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.BarberResponse;

import java.util.List;

public record BarbershopBarbersResponse(
        @PositiveOrZero
        Long barbershopId,
        @NotBlank
        String name,
        List<BarberResponse> barbers
) {
}
