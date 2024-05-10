package ru.mirea.maximister.barbershopbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.maximister.barbershopbackend.dto.ApiErrorResponse;
import ru.mirea.maximister.barbershopbackend.dto.JwtAuthenticationResponse;
import ru.mirea.maximister.barbershopbackend.dto.admin.responses.BanUserResponse;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignInRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignUpRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.UpdatePasswordRequest;
import ru.mirea.maximister.barbershopbackend.services.AuthService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {
    private final AuthService authService;
    @Operation(summary = "Sign in request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully signed in",
                    content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {


        return new ResponseEntity<>(
                authService.signIn(request),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Sign up request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully signed up",
                    content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User is alreay exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody  SignUpRequest request) { //+
        return new ResponseEntity<>(
                authService.signUp(request),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Change password request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully changed password",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request's parameters",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(request);
        return new ResponseEntity<>(
                "Password was successfully changed",
                HttpStatus.OK
        );
    }
}
