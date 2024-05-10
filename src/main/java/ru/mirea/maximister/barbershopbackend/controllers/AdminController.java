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
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.AddRoleRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.BanUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.RevokeRoleRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.responses.BanUserResponse;
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

    @Operation(summary = "Ban user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully banned",
                    content = @Content(schema = @Schema(implementation = BanUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PatchMapping("/ban")
    public ResponseEntity<?> banUser(
            @RequestBody
            @Parameter(description = "Request to ban user", required = true)
            BanUserRequest request
    ) {
        var response = adminService.banUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Add barber's role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was given new role",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User is already barber",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PatchMapping("/barbers")
    public ResponseEntity<?> addBarber(
            @RequestBody
            @Parameter(description = "Request to add barber's role to user", required = true)
            AddRoleRequest request
    ) {
        adminService.addBarber(request.email());
        return new ResponseEntity<>("User was successfully updated to barber",
                HttpStatus.OK);
    }

    @Operation(summary = "Add admin's role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was given new role",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User is already admin",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PatchMapping
    public ResponseEntity<?> addAdmin(
            @RequestBody
            @Parameter(description = "Request to add admin's role to user", required = true)
            AddRoleRequest request
    ) {
        adminService.addAdmin(request.email());
        return new ResponseEntity<>("User was successfully updated to admin",
                HttpStatus.OK);
    }

    @Operation(summary = "revoke role from user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User revoked role",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User don't have such role",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<?> revokeRole(
            @RequestBody
            @Parameter(description = "Request to revoke user's role", required = true)
            RevokeRoleRequest request
    ) {
        adminService.revokeRole(request);
        return new ResponseEntity<>(
                "Successfully revoked role",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Add new service to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service was added to system",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Service is already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/service")
    public ResponseEntity<?> addService(
            @RequestBody
            @Parameter(description = "Request to add service", required = true)
            AddServiceRequest request
    ) {
        adminService.addService(request);
        return new ResponseEntity<>(
                "Successfully added new service",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete service from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service was deleted",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Service was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(
            @RequestBody
            @Parameter(description = "Request to delete service", required = true)
            DeleteServiceRequest request
    ) {
        adminService.deleteService(request);
        return new ResponseEntity<>(
                "Successfully deleted service",
                HttpStatus.OK
        );
    }
}
