package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    //Привязка к барбершопу
    //Добавление и удаление услуг из своего списка
    //установка графика


    //+
    @PostMapping
    public ResponseEntity<?> setBarbershop(@RequestHeader(TOKEN) String token, @RequestBody SetBarberBarbershopRequest request) {
        barberService.setBarbersBarbershop(TokenExtractor.extractToken(token), request);

        return new ResponseEntity<>(
                "Successfully added barber to barbershop",
                HttpStatus.OK
        );
    }

    //+
    @PostMapping("/service")
    public ResponseEntity<?> addService(@RequestHeader(TOKEN) String token, @RequestBody AddServiceToBarberRequest request) {
        barberService.addServiceToBarber(TokenExtractor.extractToken(token), request);

        return new ResponseEntity<>(
                "Successfully added service to barber",
                HttpStatus.OK
        );
    }

    //+
    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(@RequestHeader(TOKEN) String token, @RequestBody DeleteBarbersServiceRequest request) {
        barberService.deleteBarbersService(TokenExtractor.extractToken(token), request);

        return new ResponseEntity<>(
                "Successfully deleted service to barber",
                HttpStatus.OK
        );
    }

    //+
    @PostMapping("/schedule/list")
    public ResponseEntity<?> updateSchedule(@RequestHeader(TOKEN) String token, @RequestBody UpdateScheduleListRequest request) {
        barberService.updateScheduleRequest(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully updated schedule for barber",
                HttpStatus.OK
        );
    }

    //+
    @PostMapping("/schedule")
    public ResponseEntity<?> updateSchedule(@RequestHeader(TOKEN) String token, @RequestBody UpdateScheduleRequest request) {
        barberService.updateScheduleRequest(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully updated schedule for barber",
                HttpStatus.OK
        );
    }


    @DeleteMapping("/schedule")
    public ResponseEntity<?> deleteSchedule(@RequestHeader(TOKEN) String token, @RequestBody DeleteScheduleRequest request) {
        barberService.deleteSchedule(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully deleted schedule for barber",
                HttpStatus.OK
        );
    }

    //+
    @PostMapping("/schedule/vocation")
    public ResponseEntity<?> addVocation(@RequestHeader(TOKEN) String token, @RequestBody AddVocationRequest request) {
        barberService.addVocation(TokenExtractor.extractToken(token), request);
        return new ResponseEntity<>(
                "Successfully added vocation for barber",
                HttpStatus.OK
        );
    }
}
