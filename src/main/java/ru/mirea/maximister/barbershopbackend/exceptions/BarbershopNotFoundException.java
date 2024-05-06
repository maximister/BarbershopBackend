package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class BarbershopNotFoundException extends BarbershopServerException {
    public BarbershopNotFoundException(String barbershop) {
        super(
                "Barbershop " + barbershop + " not found",
                "Can not found such barbershop",
                HttpStatus.NOT_FOUND
        );
    }
}
