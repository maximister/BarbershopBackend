package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Service;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.AddAdminRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.AddBarberRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.requests.BanUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.admin.responses.BanUserResponse;
import ru.mirea.maximister.barbershopbackend.dto.service.requests.AddServiceRequest;
import ru.mirea.maximister.barbershopbackend.dto.service.requests.DeleteServiceRequest;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;
import ru.mirea.maximister.barbershopbackend.utils.DateUtils;

import java.time.Duration;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    /**
     * Бан пользователей
     * разадача прав
     * добавление услуг в общий список
     */

    //TODO: добавить логгирование
    //TODO: ынести логику услуг отдельно

    @Transactional
    public BanUserResponse banUser(BanUserRequest request) {
        User user = userRepository.findByEmail(request.userEmail())
                //TODO: добавить свою кастомную ошибку user not found
                .orElseThrow(() -> new UsernameNotFoundException(""));

        user.setActive(false);
        userRepository.setUserIsActive(user.getId(), false);

        return new BanUserResponse(user.getEmail(), user.getFullname(), request.reason());
    }

    @Transactional
    public void addBarber(AddBarberRequest request) {
        User barber = userRepository.findByEmail(request.userEmail())
                //TODO: добавить свою кастомную ошибку user not found
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if (barber.getRoles().contains(Role.ROLE_BARBER)) {
            //TODO: кастомная ошибка userIsAlreadyBarberException
            throw new IllegalArgumentException();
        }

        barber.getRoles().add(Role.ROLE_BARBER);
        userRepository.save(barber);
    }

    @Transactional
    public void addAdmin(AddAdminRequest request) {
        User barber = userRepository.findByEmail(request.email())
                //TODO: добавить свою кастомную ошибку user not found
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if (barber.getRoles().contains(Role.ROLE_ADMIN)) {
            //TODO: кастомная ошибка userIsAlreadyBarberException
            throw new IllegalArgumentException();
        }

        barber.getRoles().add(Role.ROLE_ADMIN);
        userRepository.save(barber);
    }

    @Transactional
    public boolean addService(AddServiceRequest request) {
        try {
            DateUtils.validateServiceDuration(request.duration());
        } catch (IllegalArgumentException e) {
            log.info("Error during adding service: {}", e.getMessage());
            //TODO: сделать кастомную ошибку, привести к ней
            throw new IllegalArgumentException(e);
        }

        Service service = Service.builder()
                .price(request.price())
                .name(request.name())
                .description(request.description())
                .duration(request.duration())
                .build();

        if (serviceRepository.findByName(service.getName()).isPresent()) {
            log.info("Service {} is already exists", service.getName());
            return false;
        } else {
            serviceRepository.save(service);
            log.info("Service {} was successfully registered", service.getName());
            return true;
        }
    }

    @Transactional
    public void deleteService(DeleteServiceRequest request) {
        Service service = serviceRepository.findByName(request.name())
                .orElseThrow(() -> {
                    //TODO: астомная ошибка
                    log.info("Error during deletion service {}: no such service", request.name());
                    return new IllegalArgumentException();
                });

        serviceRepository.deleteServiceFromBarbershops(service.getId());
        serviceRepository.deleteServiceFromUsers(service.getId());
        serviceRepository.deleteById(service.getId());
        log.info("Successfully deleted service {}", request.name());

        //TODO: проверить удаление сервиса
    }
}
