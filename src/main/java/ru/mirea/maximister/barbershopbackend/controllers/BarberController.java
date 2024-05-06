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

@RestController
@RequestMapping("/barbers")
@AllArgsConstructor
public class BarberController {
    private final BarberService barberService;
    //Привязка к барбершопу
    //Добавление и удаление услуг из своего списка
    //установка графика

    @PostMapping("/barbers")
    public ResponseEntity<?> setBarbershop(@RequestBody  SetBarberBarbershopRequest request) {
        barberService.setBarbersBarbershop(request);

        return new ResponseEntity<>(
                "Successfully added barber to barbershop",
                HttpStatus.OK
        );
    }

    @PostMapping("/service")
    public ResponseEntity<?> addService(@RequestBody AddServiceToBarberRequest request) {
        barberService.addServiceToBarber(request);

        return new ResponseEntity<>(
                "Successfully added service to barber",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(@RequestBody DeleteBarbersServiceRequest request) {
        barberService.deleteBarbersService(request);

        return new ResponseEntity<>(
                "Successfully deleted service to barber",
                HttpStatus.OK
        );
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> updateSchedule(@RequestBody UpdateScheduleListRequest request) {
        barberService.updateScheduleRequest(request);
        return new ResponseEntity<>(
                "Successfully updated schedule for barber",
                HttpStatus.OK
        );
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> updateSchedule(@RequestBody UpdateScheduleRequest request) {
        barberService.updateScheduleRequest(request);
        return new ResponseEntity<>(
                "Successfully updated schedule for barber",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/schedule")
    public ResponseEntity<?> deleteSchedule(@RequestBody DeleteScheduleRequest request) {
        barberService.deleteSchedule(request);
        return new ResponseEntity<>(
                "Successfully deleted schedule for barber",
                HttpStatus.OK
        );
    }

    @PostMapping("/schedule/vocation")
    public ResponseEntity<?> addVocation(@RequestBody AddVocationRequest request) {
        barberService.addVocation(request);
        return new ResponseEntity<>(
                "Successfully added vocation for barber",
                HttpStatus.OK
        );
    }
}
