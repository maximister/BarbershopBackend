package ru.mirea.maximister.barbershopbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.ApiErrorResponse;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.requests.*;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopBarbersResponse;
import ru.mirea.maximister.barbershopbackend.dto.barbershop.responses.BarbershopList;
import ru.mirea.maximister.barbershopbackend.services.BarbershopService;

@RestController
@RequestMapping("/barbershops")
@AllArgsConstructor
public class BarbershopController {
    private final BarbershopService barbershopService;

    @Operation(summary = "add new barbershop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbershop was added",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Barbershop is already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> addBarbershop(@RequestBody AddBarbershopRequest request) {
        barbershopService.addBarbershop(request);

        return new ResponseEntity<>(
                "barbershop was successfully added",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get all barbershops list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbershop list was given",
                    content = @Content(schema = @Schema(implementation = BarbershopList.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllBarbershops() {
        return new ResponseEntity<>(
                barbershopService.getAllBarberShops(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get all barbershops by city list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbershop list was given",
                    content = @Content(schema = @Schema(implementation = BarbershopList.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{city}")
    public ResponseEntity<?> getAllBarbershopsByCity(@PathVariable String city) {
        return new ResponseEntity<>(
                barbershopService.getAllBarbershopsByCity(city),
                HttpStatus.OK
        );
    }

    @Operation(summary = "delete barbershop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbershop was deleted",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<?> deleteBarbershop(@RequestBody DeleteBarbershopRequest request) {
        barbershopService.deleteBarbershop(request);
        return new ResponseEntity<>(
                "barbershop was successfully removed",
                HttpStatus.OK
        );
    }

    //TODO: возможно добавить получение барбершопа

    @Operation(summary = "Set up barbershop's work time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barbershop's work time changed",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/time")
    public ResponseEntity<?> setBarbershopWorkTime(@RequestBody SetBarbershopWorkTimeRequest request) {
        barbershopService.setBarberShopWorkTime(request);
        return new ResponseEntity<>(
                "barbershop work time was successfully changed",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get barbershop's barbers list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return Barbers list",
                    content = @Content(schema = @Schema(implementation = BarbershopBarbersResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/barbers")
    public ResponseEntity<?> getAllBarbershopBarbers(@RequestBody GetAllBarbershopBarbersRequest request) {
        return new ResponseEntity<>(
                barbershopService.getAllBarberShopBarbers(request),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update barbershop's description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updates barbershop's description",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/description")
    public ResponseEntity<?> updateDescription(@RequestBody UpdateBarbershopDescriptionRequest request) {
        barbershopService.updateBarberShopDescription(request);
        return new ResponseEntity<>(
                "barbershop description was successfully changed",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Add service to a barbershop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "adds service to a barbershop",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Barbershop already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/service")
    public ResponseEntity<?> addService(@RequestBody AddServiceToBarbershopRequest request) {
        barbershopService.addServiceToBarbershop(request);
        return new ResponseEntity<>(
                "New service " + request.serviceName() + " was successfully added to barbershop",
                HttpStatus.OK
        );
    }

    @Operation(summary = "delete service from a barbershop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletes service to a barbershop",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Barbershop not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Service not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(@RequestBody DeleteBarbershopServiceRequest request) {
        barbershopService.deleteBarbershopService(request);
        return new ResponseEntity<>(
                "Service " + request.serviceName() + " was successfully removed from barbershop",
                HttpStatus.OK
        );
    }
}
