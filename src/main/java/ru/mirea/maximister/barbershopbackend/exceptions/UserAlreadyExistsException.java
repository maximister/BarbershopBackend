package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class UserAlreadyExistsException extends BarbershopServerException {
    public UserAlreadyExistsException(String email) {
        super(
                "User " + email + " is already exists",
                "This user is already exists",
                HttpStatus.CONFLICT
        );
    }
}
