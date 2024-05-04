package ru.mirea.maximister.barbershopbackend.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_BARBER;

    @Override
    public String getAuthority() {
        return name();
    }
}
