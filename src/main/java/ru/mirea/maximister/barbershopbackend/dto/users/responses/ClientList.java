package ru.mirea.maximister.barbershopbackend.dto.users.responses;

import java.util.List;

public record ClientList(
        List<ClientResponse> clients
) {
}
