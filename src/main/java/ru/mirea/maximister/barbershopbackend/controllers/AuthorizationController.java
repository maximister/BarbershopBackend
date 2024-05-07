package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignInRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignUpRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.UpdatePasswordRequest;
import ru.mirea.maximister.barbershopbackend.services.AuthService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {
    private final AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody  SignInRequest request) {


        return new ResponseEntity<>(
                authService.signIn(request),
                HttpStatus.OK
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody  SignUpRequest request) {
        return new ResponseEntity<>(
                authService.signUp(request),
                HttpStatus.OK
        );
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(request);
        return new ResponseEntity<>(
                "Password was successfully changed",
                HttpStatus.OK
        );
    }
}
