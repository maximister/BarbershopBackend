package ru.mirea.maximister.barbershopbackend.dto.admin.responses;

public record BanUserResponse(
        String email,
        String fullname,
        String reason
) {
}
