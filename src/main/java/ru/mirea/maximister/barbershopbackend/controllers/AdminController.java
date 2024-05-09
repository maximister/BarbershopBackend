package ru.mirea.maximister.barbershopbackend.controllers;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.AddRoleRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.BanUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.RevokeRoleRequest;
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
    public ResponseEntity<?> addBarber(@RequestBody AddRoleRequest request) {
        adminService.addBarber(request.email());
        return new ResponseEntity<>("User was successfully updated to barber",
                HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> addAdmin(@RequestBody AddRoleRequest request) {
        adminService.addAdmin(request.email());
        return new ResponseEntity<>("User was successfully updated to admin",
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> revokeRole(@RequestBody RevokeRoleRequest request) {
        adminService.revokeRole(request);
        return new ResponseEntity<>(
                "Successfully revoked role",
                HttpStatus.OK
        );
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
