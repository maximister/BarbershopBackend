package ru.mirea.maximister.barbershopbackend.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dto with list of requests to update schedule")
public record UpdateScheduleListRequest(
        @Schema(description = "List of update schedule requests")
        List<UpdateScheduleRequest> requests
) {
}
