package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class BarbershopException extends BarbershopServerException {
    public BarbershopException(String message) {
        super(
                message,
                "Error related to barbershop",
                HttpStatus.CONFLICT
        );
    }
}
