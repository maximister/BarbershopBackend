package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignInRequest;
import ru.mirea.maximister.barbershopbackend.dto.auth.SignUpRequest;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    //TODO: Добавить Jwt

    @Transactional
    public boolean signUp(SignUpRequest request) {
        User user = User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .fullname(request.fullname())
                .phoneNumber(request.phoneNumber())
                .isActive(true) //TODO: добавить активацию после подтверждения аккаунта по почте
                .build();

        user.getRoles().add(Role.ROLE_USER);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        } else {
            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    public boolean signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + request.email() + " not found")
        );

        if (!encoder.encode(request.password()).equals(user.getPassword())) {
            //TODO: добавить ошибку
            return false;
        }

        return true;
    }
}
