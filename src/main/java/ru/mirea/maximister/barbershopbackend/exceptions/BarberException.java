package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class BarberException extends BarbershopServerException {
    public BarberException(String message) {
        super(
                message,
                "Error related to barber",
                HttpStatus.CONFLICT
        );
    }
}