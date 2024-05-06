package ru.mirea.maximister.barbershopbackend.exceptions;

import org.springframework.http.HttpStatus;

public final class PasswordMatchingException extends BarbershopServerException {
    public PasswordMatchingException() {
        super(
                "You entered invalid password",
                "Error during password matching",
                HttpStatus.BAD_REQUEST
        );
    }
}
