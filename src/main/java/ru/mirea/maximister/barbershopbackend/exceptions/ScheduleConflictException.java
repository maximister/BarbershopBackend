package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class ScheduleConflictException extends BarbershopServerException {
    public ScheduleConflictException(String message) {
        super(
                message,
                "Error during updating schedule: schedule in this time is already exists",
                HttpStatus.CONFLICT
        );
    }
}
