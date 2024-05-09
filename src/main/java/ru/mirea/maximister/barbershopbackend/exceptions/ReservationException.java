package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class ReservationException extends BarbershopServerException {
    public ReservationException(String message) {
        super(
                message,
                "Exception related to reservations",
                HttpStatus.BAD_REQUEST
        );
    }
}
