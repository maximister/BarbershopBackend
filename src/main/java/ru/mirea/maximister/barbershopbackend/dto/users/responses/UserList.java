package ru.mirea.maximister.barbershopbackend.dto.users.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dto with list of users")

public record UserList(
        @Schema(description = "List of users")
        List<UserDto> users
) {
}
