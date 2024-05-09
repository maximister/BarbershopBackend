package ru.mirea.maximister.barbershopbackend.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;
import ru.mirea.maximister.barbershopbackend.services.UserService;

@Component
@RequiredArgsConstructor
@Log4j2
public class SeedConfiguration implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .fullname("admin")
                    .email("admin@admin.com")
                    .active(true)
                    .phoneNumber("88888888")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            admin.getRoles().add(Role.ROLE_ADMIN);
            userRepository.save(admin);
            log.debug("created main ADMIN user - {}", admin);
        }
    }
}
