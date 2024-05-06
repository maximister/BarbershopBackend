package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignInRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignUpRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.UpdatePasswordRequest;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    //TODO: Добавить Jwt!!!

    @Transactional
    public boolean signUp(SignUpRequest request) {
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
            return false;
        } else {
            userRepository.save(user);
            log.info("User {} was signed up", request.email());
            return true;
        }
    }

    @Transactional
    public boolean signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + request.email() + " not found")
        );

        if (!encoder.matches(request.password(), user.getPassword())) {
            //TODO: добавить ошибку
            return false;
        }

        return true;
    }

    @Transactional
    public boolean updatePassword(UpdatePasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(
                        //TODO: кастомная ошибка
                        () -> new UsernameNotFoundException("")
                );

        if (!encoder.matches(request.oldPassword(), user.getPassword())) {
            //TODO: кастомная ошибка о том что пароли не сходятся
            log.info("Received invalid password during changing password for user {}",
                    request.email());
            throw new IllegalArgumentException("");
        }

        if (request.oldPassword().equals(request.newPassword())) {
            //TODO: кастомная ошибка о том что пароли однаковые
            log.info("Received equal passwords during changing password for user {}",
                    request.email());
            throw new IllegalArgumentException("");
        }

        user.setPassword(request.newPassword());
        userRepository.setUserPassword(user.getId(), encoder.encode(request.newPassword()));
        log.info("Changed password for user {}", user.getEmail());

        return true;
    }
}
