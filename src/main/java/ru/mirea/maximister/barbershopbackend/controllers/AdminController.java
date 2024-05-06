package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.AddAdminRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.AddBarberRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.BanUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.service.requests.AddServiceRequest;
import ru.mirea.maximister.barbershopbackend.dto.service.requests.DeleteServiceRequest;
import ru.mirea.maximister.barbershopbackend.services.AdminService;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    //повышение пользователя
    //расширение списка услуг и товаров
    //бан пользователя
    //мб одобрение всяких мувов

    @PatchMapping("/ban")
    public ResponseEntity<?> banUser(@RequestBody BanUserRequest request) {
        var response = adminService.banUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/barbers")
    public ResponseEntity<?> addBarber(@RequestBody AddBarberRequest request) {
        adminService.addBarber(request);
        return new ResponseEntity<>("User was successfully updated to barber",
                HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> addAdmin(@RequestBody AddAdminRequest request) {
        adminService.addAdmin(request);
        return new ResponseEntity<>("User was successfully updated to admin",
                HttpStatus.OK);
    }

    @PostMapping("/service")
    public ResponseEntity<?> addService(@RequestBody AddServiceRequest request) {
        adminService.addService(request);
        return new ResponseEntity<>(
                "Successfully added new service",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(@RequestBody DeleteServiceRequest request) {
        adminService.deleteService(request);
        return new ResponseEntity<>(
                "Successfully deleted service",
                HttpStatus.OK
        );
    }
}
