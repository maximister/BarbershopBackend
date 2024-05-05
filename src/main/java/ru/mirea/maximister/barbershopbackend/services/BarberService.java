package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.barber.requests.SetBarberBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class BarberService {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final BarbershopService barbershopService;

    /**
     * приписка и смена точки
     * расширение своего списка услуг
     * установка расписания
     */

    @Transactional
    public void setBarbersBarbershop(SetBarberBarbershopRequest request) {
        Barbershop barbershop = barbershopService.getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        User barber = userRepository.findByEmail(request.barberEmail()).orElseThrow(
                () -> {
                    log.info("Error while finding barber {}", request.barberEmail());
                    //TODO: кастомная ошибка
                    return new IllegalArgumentException();
                }
        );

        if (!barber.getRoles().contains(Role.ROLE_BARBER)) {
            //TODO: кастомная ошибка
            log.warn("User {} is not a barber", request.barberEmail());
            throw new IllegalArgumentException();
        }

        barbershop.addBarber(barber);
        barbershopService.updateBarbershop(barbershop);
        userRepository.save(barber);
        log.info("Successfully added barber {} to barbershop {}",
                barber.getEmail(),
                barbershop.getAddress()
        );
    }

    //TODO: расписать добавление сервайсов барберу с проверкой того, что он приписан к шопу
    // и у шопа есть такая услуга
}
