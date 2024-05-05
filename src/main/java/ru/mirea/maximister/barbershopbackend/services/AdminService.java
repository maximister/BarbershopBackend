package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.admin.AddAdminRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.AddBarberRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.BanUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.BanUserResponse;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    /**
     * Бан пользователей
     * разадача прав
     * добавление услуг в общий список
     */

    //TODO: б убрать Response entity

    @Transactional
    public ResponseEntity<BanUserResponse> banUser(BanUserRequest request) {
        User user = userRepository.findByEmail(request.userEmail())
                //TODO: добавить свою кастомную ошибку user not found
                .orElseThrow(() -> new UsernameNotFoundException(""));

        user.setActive(false);
        userRepository.setUserIsActive(user.getId(), false);

        return new ResponseEntity<>(
             new BanUserResponse(user.getEmail(), user.getFullname(), request.reason()),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<Void> addBarber(AddBarberRequest request) {
        User barber = userRepository.findByEmail(request.userEmail())
                //TODO: добавить свою кастомную ошибку user not found
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if (barber.getRoles().contains(Role.ROLE_BARBER)) {
            //TODO: кастомная ошибка userIsAlreadyBarberException
            throw new IllegalArgumentException();
        }

        barber.getRoles().add(Role.ROLE_BARBER);
        userRepository.save(barber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> addAdmin(AddAdminRequest request) {
        User barber = userRepository.findByEmail(request.email())
                //TODO: добавить свою кастомную ошибку user not found
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if (barber.getRoles().contains(Role.ROLE_ADMIN)) {
            //TODO: кастомная ошибка userIsAlreadyBarberException
            throw new IllegalArgumentException();
        }

        barber.getRoles().add(Role.ROLE_ADMIN);
        userRepository.save(barber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<> addService() {
        //TODO: do!
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    public ResponseEntity<> deleteService() {
        //TODO: do!
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}
