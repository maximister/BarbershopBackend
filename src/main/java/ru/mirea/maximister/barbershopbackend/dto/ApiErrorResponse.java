package ru.mirea.maximister.barbershopbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dto with information about server's error")
public record ApiErrorResponse(
        @Schema(description = "Error's description")
        String description,
        @Schema(description = "Error's HTTP status code")
        String code,
        @Schema(description = "Error's name")
        String exceptionName,
        @Schema(description = "Error's message")
        String exceptionMessage,
        @Schema(description = "Error's stacktrace")
        List<String> stacktrace
) {
}