package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.AddReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.DenyReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.GetBarberSlotsRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.GetSlotsInBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.services.ReservationService;
import ru.mirea.maximister.barbershopbackend.utils.TokenExtractor;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private static final String TOKEN = "Authorization";

    //+
    @PostMapping
    public ResponseEntity<?> addReservation(
            @RequestHeader(TOKEN) String token,
            @RequestBody AddReservationRequest request) {
        reservationService.AddReservation(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Reservation was successfully added",
                HttpStatus.OK
        );
    }


    //+
    @DeleteMapping
    public ResponseEntity<?> denyReservation(
            @RequestHeader(TOKEN) String token,
            @RequestBody DenyReservationRequest request) {
        reservationService.denyReservation(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Reservation was successfully denied",
                HttpStatus.OK
        );
    }

    //+
    @GetMapping
    public ResponseEntity<?> getClientsActiveReservations(@RequestHeader(TOKEN) String token) {
        return new ResponseEntity<>(
                reservationService.getClientsActiveReservations(TokenExtractor.extractToken(token)),
                HttpStatus.OK
        );
    }

    //+
    @GetMapping("/barber")
    public ResponseEntity<?> getBarbersReservations(@RequestHeader(TOKEN) String token) {
        return new ResponseEntity<>(
                reservationService.getBarbersReservations(TokenExtractor.extractToken(token)),
                HttpStatus.OK
        );
    }

    //+
    @GetMapping("/slots/barber")
    public ResponseEntity<?> getBarbersSlots(@RequestBody GetBarberSlotsRequest request) {
        return new ResponseEntity<>(
                reservationService.getBarbersSlots(request.email()),
                HttpStatus.OK
        );
    }

    //+
    @GetMapping("/slots/barbershop")
    public ResponseEntity<?> getAllSlotsInBarbershop(@RequestBody GetSlotsInBarbershopRequest request) {
        return new ResponseEntity<>(
                reservationService.getAllSlotsInBarbershop(request),
                HttpStatus.OK
        );
    }
}
