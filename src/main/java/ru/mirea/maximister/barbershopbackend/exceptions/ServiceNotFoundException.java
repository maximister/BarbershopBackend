package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class ServiceNotFoundException extends BarbershopServerException {
    public ServiceNotFoundException(String name) {
        super(
                "Service " + name + " not found",
                "Can not found such service",
                HttpStatus.NOT_FOUND
        );
    }
}
