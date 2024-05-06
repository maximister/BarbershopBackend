package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class ServiceAlreadyExistsException extends BarbershopServerException {
    public ServiceAlreadyExistsException(String service) {
        super(
                "Service " + service + " is already exists",
                "This service is already exists",
                HttpStatus.CONFLICT
        );
    }
}
