package ru.mirea.maximister.barbershopbackend.dto.users.responses;

public record UserDto(
        Long id,
        String email,
        String phoneNumber,
        String fullname
) {
}
