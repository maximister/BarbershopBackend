package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class ServerDateTimeException extends BarbershopServerException {
    public ServerDateTimeException(String message) {
        super(
                message,
                "Error related to date and time aspects",
                HttpStatus.BAD_REQUEST
        );
    }
}