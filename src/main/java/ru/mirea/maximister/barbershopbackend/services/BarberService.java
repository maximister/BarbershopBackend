package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.domain.Schedule;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.barber.AddServiceToBarberRequest;
import ru.mirea.maximister.barbershopbackend.dto.barber.DeleteBarbersServiceRequest;
import ru.mirea.maximister.barbershopbackend.dto.barber.SetBarberBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.AddVocationRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.DeleteScheduleRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.UpdateScheduleListRequest;
import ru.mirea.maximister.barbershopbackend.dto.schedule.UpdateScheduleRequest;
import ru.mirea.maximister.barbershopbackend.repository.ScheduleRepository;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;
import ru.mirea.maximister.barbershopbackend.utils.DateUtils;

import java.time.LocalDate;
import java.time.OffsetTime;

@Service
@Slf4j
@AllArgsConstructor
public class BarberService {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final BarbershopService barbershopService;
    private final ScheduleRepository scheduleRepository;

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

        User barber = getBarber(request.barberEmail());

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

    private User getBarber(String email) {
        User barber = userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.info("Error while searching barber {}", email);
                    //TODO: кастомная ошибка
                    return new IllegalArgumentException();
                }
        );

        if (!barber.getRoles().contains(Role.ROLE_BARBER)) {
            log.info("User {} is not a barber", email);
            //TODO: кастомная ошибка
            throw new IllegalArgumentException();
        }

        return barber;
    }

    @Transactional
    public void addServiceToBarber(AddServiceToBarberRequest request) {
        User barber = getBarber(request.barberEmail());
        Barbershop barbershop = barber.getBarbershop();

        if (barbershop == null) {
            log.info("Can not add service to barber {} because barber has no barbershop",
                    request.barberEmail());
            //TODO: кастомная ошибка
            throw new IllegalArgumentException();
        }

        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName())
                .orElseThrow(() -> {
                    log.info("No such service {}", request.serviceName());
                    //TODO: custom error
                    return new IllegalArgumentException();
                });

        if (!barbershop.getServices().contains(service)) {
            log.info("No such service {} in barbershop {}", service.getName(), barbershop.getAddress());
            //TODO: custom error
            throw new IllegalArgumentException();
        }

        barber.addService(service);
        userRepository.save(barber);
        serviceRepository.save(service);
        log.info("Successfully added service {} to barber {}",
                service.getName(), barber.getEmail());
    }

    @Transactional
    public void deleteBarbersService(DeleteBarbersServiceRequest request) {
        User barber = getBarber(request.barberEmail());
        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName())
                .orElseThrow(() -> {
                    log.info("No such service {}", request.serviceName());
                    //TODO: custom error
                    return new IllegalArgumentException();
                });

        if (!barber.getServices().contains(service)) {
            log.info("Barber {} has no such service {}",
                    barber.getEmail(), service.getName());

            //TODO: custom exception
            throw new IllegalArgumentException();
        }

        barber.deleteService(service);
        userRepository.save(barber);
        serviceRepository.save(service);
        log.info("Service {} was successfully deleted from barber {}",
                service.getName(), barber.getEmail());
    }

    @Transactional
    public void updateScheduleRequest(UpdateScheduleListRequest requests) {
        for (var request : requests.requests()) updateScheduleRequest(request);
    }

    @Transactional
    public void updateScheduleRequest(UpdateScheduleRequest request) {
        validateTime(request.from(), request.to());

        LocalDate curDate = DateUtils.getClosestDateByDayOfWeek(request.dayOfWeek());
        User barber = getBarber(request.email());

        //Добавляем расписание на 4 недели вперед
        for (int i = 0; i < 4; i++) {
            curDate = curDate.plusWeeks(i);
            OffsetTime start = request.from();
            OffsetTime end = request.to().minusMinutes(15);
            while (start.isBefore(end) || start.isEqual(end)) {
                Schedule scheduleUnit = Schedule.builder()
                        .barberId(barber.getId())
                        .date(curDate)
                        .time(start)
                        .status(true)
                        .build();
                scheduleRepository.save(scheduleUnit);
                start = start.plusMinutes(15);
            }
        }

        log.info("Successfully updated schedule: {}", request);
    }

    @Transactional
    public void deleteScheduleRequest(DeleteScheduleRequest request) {
        validateTime(request.from(), request.to());

        LocalDate curDate = DateUtils.getClosestDateByDayOfWeek(request.dayOfWeek());
        User barber = getBarber(request.email());

        for (int i = 0; i < 4; i++) {
            scheduleRepository.deleteByBarberIdAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTime(
                    barber.getId(), curDate, request.from(), request.to()
            );
        }

        log.info("Successfully deleted schedule: {}", request);
    }

    @Transactional
    public void addVocation(AddVocationRequest request) {
        validateTime(request.from(), request.to());

        LocalDate curDate = DateUtils.getClosestDateByDayOfWeek(request.dayOfWeek());
        User barber = getBarber(request.email());

        for (int i = 0; i < 4; i++) {
            scheduleRepository.updateStatusByBarberIdAndDateAndTimeRange(
                    false,
                    barber.getId(),
                    curDate,
                    request.from(),
                    request.to()
            );
        }

        log.info("Successfully deleted schedule: {}", request);
    }

    private void validateTime(OffsetTime from, OffsetTime to) {
        if (from.isBefore(to)) {
            log.info("Invalid time format: to is less than from");
            throw new IllegalArgumentException();
        }
        try {
            DateUtils.validateScheduleTime(to);
            DateUtils.validateScheduleTime(from);
        } catch (IllegalArgumentException e) {
            log.info("Invalid time format: {}", e.getMessage());
            //TODO: кастомная ошибка
            throw new IllegalArgumentException(e);
        }
    }
}