package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dto with list of All barbershops")
public record BarbershopList(
        @Schema(description = "List of All barbershops")
        List<BarbershopDto> barbershops
) {
}
