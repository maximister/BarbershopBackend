package ru.mirea.maximister.barbershopbackend.dto.schedule;

import java.util.List;

public record UpdateScheduleListRequest(
        List<UpdateScheduleRequest> requests
) {
}
