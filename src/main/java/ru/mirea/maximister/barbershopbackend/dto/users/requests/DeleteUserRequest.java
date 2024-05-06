package ru.mirea.maximister.barbershopbackend.dto.users.requests;

import jakarta.validation.constraints.Email;


//TODO: удалить, использовать просто строку!
public record DeleteUserRequest(
        @Email
        String email
) {
}
