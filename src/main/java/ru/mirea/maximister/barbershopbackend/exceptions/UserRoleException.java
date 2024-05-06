package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class UserRoleException extends BarbershopServerException {
    public UserRoleException(String message) {
        super(
                message,
                "Error related to user's role",
                HttpStatus.CONFLICT
        );
    }
}
