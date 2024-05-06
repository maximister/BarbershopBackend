package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.AddReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.DenyReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.GetSlotsInBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.services.ReservationService;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {
    //работа с записями
    //мб сделать еще один для админских задач

    private final ReservationService reservationService;

    //для всех зареганных
    @PostMapping
    public ResponseEntity<?> addReservation(@RequestBody AddReservationRequest request) {
        reservationService.AddReservation(request);
        return new ResponseEntity<>(
                "Reservation was successfully added",
                HttpStatus.OK
        );
    }

    //user
    @DeleteMapping
    public ResponseEntity<?> denyReservation(@RequestBody DenyReservationRequest request) {
        reservationService.denyReservation(request);
        return new ResponseEntity<>(
                "Reservation was successfully denied",
                HttpStatus.OK
        );
    }

    //user свое, мб сделать метод для админа с любым расписанием
    //TODO: для админа добавить в admin controller
    //TODO: для админа сделать контроллер с юзерами
    @GetMapping
    //заменить на почте из токена
    public ResponseEntity<?> getClientsActiveReservations(@RequestBody String email) {
        return new ResponseEntity<>(
                reservationService.getClientsActiveReservations(email),
                HttpStatus.OK
        );
    }

    //барбер и админ
    @GetMapping("/barber")
    public ResponseEntity<?> getBarbersReservations(@RequestBody String email) {
        return new ResponseEntity<>(
                reservationService.getBarbersReservations(email),
                HttpStatus.OK
        );
    }

    //все
    @GetMapping("/slots/barber")
    public ResponseEntity<?> getBarbersSlots(@RequestBody String email) {
        return new ResponseEntity<>(
                reservationService.getBarbersSlots(email),
                HttpStatus.OK
        );
    }

    //все
    @GetMapping("/slots/barbershop")
    public ResponseEntity<?> getAllSlotsInBarbershop(@RequestBody GetSlotsInBarbershopRequest request) {
        return new ResponseEntity<>(
                reservationService.getAllSlotsInBarbershop(request),
                HttpStatus.OK
        );
    }
}
