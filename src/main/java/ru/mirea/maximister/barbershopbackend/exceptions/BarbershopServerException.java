package ru.mirea.maximister.barbershopbackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BarbershopServerException extends RuntimeException {
    protected final String description;
    protected final HttpStatus httpStatusCode;

    public BarbershopServerException(String message, String description, HttpStatus httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.description = description;
    }

}