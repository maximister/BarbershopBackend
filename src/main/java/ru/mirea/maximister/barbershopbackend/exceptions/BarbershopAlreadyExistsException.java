package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class BarbershopAlreadyExistsException extends BarbershopServerException {
    public BarbershopAlreadyExistsException(String message) {
        super(
                "Barbershop " + message + " is already exists",
                "This barbershop is already exists",
                HttpStatus.CONFLICT
        );
    }
}
