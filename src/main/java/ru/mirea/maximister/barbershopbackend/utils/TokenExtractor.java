package ru.mirea.maximister.barbershopbackend.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenExtractor {
    public static String extractToken(String authToken) {
        return authToken.split(" ")[1];
    }
}
