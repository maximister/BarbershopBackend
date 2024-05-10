package ru.mirea.maximister.barbershopbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.ApiErrorResponse;
import ru.mirea.maximister.barbershopbackend.dto.JwtAuthenticationResponse;
import ru.mirea.maximister.barbershopbackend.dto.barber.AddServiceToBarberRequest;
import ru.mirea.maximister.barbershopbackend.dto.barber.DeleteBarbersServiceRequest;
import ru.mirea.maximister.barbershopbackend.dto.barber.SetBarberBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.AddVocationRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.DeleteScheduleRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.UpdateScheduleListRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.UpdateScheduleRequest;
import ru.mirea.maximister.barbershopbackend.services.BarberService;
import ru.mirea.maximister.barbershopbackend.utils.TokenExtractor;

@RestController
@RequestMapping("/barbers")
@AllArgsConstructor
public class BarberController {
    private final BarberService barberService;
    private static final String TOKEN = "Authorization";

    @Operation(summary = "Set barber's barbershop request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbershop was successfully set up",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> setBarbershop(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            SetBarberBarbershopRequest request) {
        barberService.setBarbersBarbershop(TokenExtractor.extractToken(token), request);

        return new ResponseEntity<>(
                "Successfully added barber to barbershop",
                HttpStatus.OK
        );
    }

    @Operation(summary = "add barber's service request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service was successfully added",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Service was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/service")
    public ResponseEntity<?> addService(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            AddServiceToBarberRequest request
    ) {
        barberService.addServiceToBarber(TokenExtractor.extractToken(token), request);

        return new ResponseEntity<>(
                "Successfully added service to barber",
                HttpStatus.OK
        );
    }

    @Operation(summary = "delete barber's service request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service was successfully deleted",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Service was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            DeleteBarbersServiceRequest request
    ) {
        barberService.deleteBarbersService(TokenExtractor.extractToken(token), request);

        return new ResponseEntity<>(
                "Successfully deleted service to barber",
                HttpStatus.OK
        );
    }

    @Operation(summary = "update barber's schedule request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule was successfully updated",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Schedule is already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/schedule/list")
    public ResponseEntity<?> updateSchedule(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            UpdateScheduleListRequest request
    ) {
        barberService.updateScheduleRequest(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully updated schedule for barber",
                HttpStatus.OK
        );
    }

    @Operation(summary = "update barber's schedule request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule was successfully updated",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Schedule is already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/schedule")
    public ResponseEntity<?> updateSchedule(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            UpdateScheduleRequest request
    ) {
        barberService.updateScheduleRequest(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully updated schedule for barber",
                HttpStatus.OK
        );
    }


    @Operation(summary = "delete barber's schedule request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule was successfully updated",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/schedule")
    public ResponseEntity<?> deleteSchedule(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            DeleteScheduleRequest request
    ) {
        barberService.deleteSchedule(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully deleted schedule for barber",
                HttpStatus.OK
        );
    }

    @Operation(summary = "add barber's vocation request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "vocation was successfully added",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/schedule/vocation")
    public ResponseEntity<?> addVocation(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody
            AddVocationRequest request
    ) {
        barberService.addVocation(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully added vocation for barber",
                HttpStatus.OK
        );
    }
}
