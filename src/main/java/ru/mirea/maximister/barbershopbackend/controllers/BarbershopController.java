package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.*;
import ru.mirea.maximister.barbershopbackend.services.BarbershopService;

@RestController
@RequestMapping("/barbershops")
@AllArgsConstructor
public class BarbershopController {
    private final BarbershopService barbershopService;

    //Доступ у админа
    @PostMapping
    public ResponseEntity<?> addBarbershop(@RequestBody AddBarbershopRequest request) { //+
        barbershopService.addBarbershop(request);

        return new ResponseEntity<>(
                "barbershop was successfully added",
                HttpStatus.OK
        );
    }

    //юзеры
    @GetMapping
    public ResponseEntity<?> getAllBarbershops() { //+
        return new ResponseEntity<>(
                barbershopService.getAllBarberShops(),
                HttpStatus.OK
        );
    }

    //юзеры
    @GetMapping("/{city}")
    public ResponseEntity<?> getAllBarbershopsByCity(@PathVariable String city) { //+
        return new ResponseEntity<>(
                barbershopService.getAllBarbershopsByCity(city),
                HttpStatus.OK
        );
    }

    //админ
    @DeleteMapping
    public ResponseEntity<?> deleteBarbershop(@RequestBody DeleteBarbershopRequest request) { //+
        barbershopService.deleteBarbershop(request);
        return new ResponseEntity<>(
                "barbershop was successfully removed",
                HttpStatus.OK
        );
    }

    //TODO: возможно добавить получение барбершопа

    //admin
    @PostMapping("/time")
    public ResponseEntity<?> setBarbershopWorkTime(@RequestBody SetBarbershopWorkTimeRequest request) {//+
        barbershopService.setBarberShopWorkTime(request);
        return new ResponseEntity<>(
                "barbershop work time was successfully changed",
                HttpStatus.OK
        );
    }

    @GetMapping("/barbers")
    public ResponseEntity<?> getAllBarbershopBarbers(@RequestBody GetAllBarbershopBarbersRequest request) {
        return new ResponseEntity<>(
                barbershopService.getAllBarberShopBarbers(request),
                HttpStatus.OK
        );
    }

    @PostMapping("/description")
    public ResponseEntity<?> updateDescription(@RequestBody UpdateBarbershopDescriptionRequest request) {
        barbershopService.updateBarberShopDescription(request);
        return new ResponseEntity<>(
                "barbershop description was successfully changed",
                HttpStatus.OK
        );
    }

    @PostMapping("/service")
    public ResponseEntity<?> addService(@RequestBody AddServiceToBarbershopRequest request) { //+
        barbershopService.addServiceToBarbershop(request);
        return new ResponseEntity<>(
                "New service " + request.serviceName() + " was successfully added to barbershop",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(@RequestBody DeleteBarbershopServiceRequest request) { //+
        barbershopService.deleteBarbershopService(request);
        return new ResponseEntity<>(
                "Service " + request.serviceName() + " was successfully removed from barbershop",
                HttpStatus.OK
        );
    }
}
