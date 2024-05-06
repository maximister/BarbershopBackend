package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class UserNotFoundException extends BarbershopServerException {
    public UserNotFoundException(String email) {
        super(
                "User " + email + " not found",
                "Can not found such user",
                HttpStatus.NOT_FOUND
        );
    }
}