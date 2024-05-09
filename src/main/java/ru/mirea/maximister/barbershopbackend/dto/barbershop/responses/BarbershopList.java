package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

import java.util.List;

public record BarbershopList(
        List<BarbershopDto> barbershops
) {
}
