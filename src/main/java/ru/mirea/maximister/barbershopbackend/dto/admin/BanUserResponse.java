package ru.mirea.maximister.barbershopbackend.dto.admin;

public record BanUserResponse(
        String email,
        String fullname,
        String reason
) {
}
