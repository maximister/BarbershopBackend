package ru.mirea.maximister.barbershopbackend.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserDto;

import java.util.List;

@Schema(description = "Dto which contains barber's free time slots")
public record FreeBarbersSlots(
        @NotNull
        //TODO: добавить ссылку на userDto
        @Schema(description = "Barber's information")
        UserDto barber,
        @Schema(description = "Barber's free slots")
        List<FreeSlot> slots
) {
}
