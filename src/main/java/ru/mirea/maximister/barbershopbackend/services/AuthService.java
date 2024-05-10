package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.JwtAuthenticationResponse;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignInRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignUpRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.UpdatePasswordRequest;
import ru.mirea.maximister.barbershopbackend.exceptions.PasswordMatchingException;
import ru.mirea.maximister.barbershopbackend.exceptions.UserAlreadyExistsException;
import ru.mirea.maximister.barbershopbackend.exceptions.UserNotFoundException;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;
import ru.mirea.maximister.barbershopbackend.services.security.JwtService;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .fullname(request.fullname())
                .phoneNumber(request.phoneNumber())
                .active(true) //TODO: добавить активацию после подтверждения аккаунта по почте
                .build();

        user.getRoles().add(Role.ROLE_USER);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.info("User {} is already registered", request.email());
            throw new UserAlreadyExistsException(request.email());
        }

        userRepository.save(user);
        log.info("User {} was signed up", request.email());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    @Transactional
    public JwtAuthenticationResponse  signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UserNotFoundException(request.email())
        );

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));

        if (!encoder.matches(request.oldPassword(), user.getPassword())) {
            log.info("Received invalid password during changing password for user {}",
                    request.email());
            throw new PasswordMatchingException();
        }

        if (request.oldPassword().equals(request.newPassword())) {
            log.info("Received equal passwords during changing password for user {}",
                    request.email());
            throw new PasswordMatchingException();
        }

        user.setPassword(request.newPassword());
        userRepository.setUserPassword(user.getId(), encoder.encode(request.newPassword()));
        log.info("Changed password for user {}", user.getEmail());
    }
}
