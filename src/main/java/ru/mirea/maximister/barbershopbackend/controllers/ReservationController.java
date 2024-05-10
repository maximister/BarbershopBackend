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
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.AddReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.DenyReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.GetBarberSlotsRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.GetSlotsInBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.responses.BarberFreeSlotsResponse;
import ru.mirea.maximister.barbershopbackend.dto.reservation.responses.BarberReservationsList;
import ru.mirea.maximister.barbershopbackend.dto.reservation.responses.ClientReservationList;
import ru.mirea.maximister.barbershopbackend.dto.reservation.responses.SlotsInBarbershopResponse;
import ru.mirea.maximister.barbershopbackend.services.ReservationService;
import ru.mirea.maximister.barbershopbackend.utils.TokenExtractor;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private static final String TOKEN = "Authorization";

    @Operation(summary = "add new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "adds new reservation",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Reservation in this time already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> addReservation(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody AddReservationRequest request
    ) {
        reservationService.AddReservation(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Reservation was successfully added",
                HttpStatus.OK
        );
    }

    @Operation(summary = "deny reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "denies new reservation",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @DeleteMapping
    public ResponseEntity<?> denyReservation(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token,
            @RequestBody DenyReservationRequest request
    ) {
        reservationService.denyReservation(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Reservation was successfully denied",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get clients Reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns clients reservations",
                    content = @Content(schema = @Schema(implementation = ClientReservationList.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getClientsActiveReservations(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token
    ) {
        return new ResponseEntity<>(
                reservationService.getClientsActiveReservations(TokenExtractor.extractToken(token)),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get barbers Reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns barbers reservations",
                    content = @Content(schema = @Schema(implementation = BarberReservationsList.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/barber")
    public ResponseEntity<?> getBarbersReservations(
            @RequestHeader(TOKEN)
            @Parameter(description = "Jwt token", required = true)
            String token
    ) {
        return new ResponseEntity<>(
                reservationService.getBarbersReservations(TokenExtractor.extractToken(token)),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get barbers free slots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns barbers reservations",
                    content = @Content(schema = @Schema(implementation = BarberFreeSlotsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/slots/barber")
    public ResponseEntity<?> getBarbersSlots(@RequestBody GetBarberSlotsRequest request) {
        return new ResponseEntity<>(
                reservationService.getBarbersSlots(request.email()),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get barbershop's free slots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns barbers reservations",
                    content = @Content(schema = @Schema(implementation = SlotsInBarbershopResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/slots/barbershop")
    public ResponseEntity<?> getAllSlotsInBarbershop(@RequestBody GetSlotsInBarbershopRequest request) {
        return new ResponseEntity<>(
                reservationService.getAllSlotsInBarbershop(request),
                HttpStatus.OK
        );
    }
}
