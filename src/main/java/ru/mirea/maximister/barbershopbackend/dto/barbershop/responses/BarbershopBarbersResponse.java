package ru.mirea.maximister.barbershopbackend.dto.barbershop.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

import java.util.List;

@Schema(description = "Dto with list of barbershop's barbers")
public record BarbershopBarbersResponse(
        @PositiveOrZero
        @Schema(description = "Barbershop's id", example = "611")
        Long barbershopId,
        @NotBlank
        @Schema(description = "Barbershop's name", example = "Men's Choice")
        String name,
        @Schema(description = "Barbershop's barbers list")
        List<UserDto> barbers
) {
}
