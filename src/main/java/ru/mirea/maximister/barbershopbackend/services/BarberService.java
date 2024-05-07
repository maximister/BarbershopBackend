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
import ru.mirea.maximister.barbershopbackend.exceptions.BarberException;
import ru.mirea.maximister.barbershopbackend.exceptions.BarbershopException;
import ru.mirea.maximister.barbershopbackend.exceptions.UserNotFoundException;
import ru.mirea.maximister.barbershopbackend.exceptions.UserRoleException;
import ru.mirea.maximister.barbershopbackend.repository.ScheduleRepository;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;
import ru.mirea.maximister.barbershopbackend.services.security.JwtService;
import ru.mirea.maximister.barbershopbackend.utils.DateUtils;

import java.time.DateTimeException;
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
    private final JwtService jwtService;

    /**
     * приписка и смена точки
     * расширение своего списка услуг
     * установка расписания
     */

    @Transactional
    public void setBarbersBarbershop(String token, SetBarberBarbershopRequest request) {
        Barbershop barbershop = barbershopService.getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );

        String email = jwtService.extractUserName(token);
        User barber = getBarber(email);

        if (!barber.getRoles().contains(Role.ROLE_BARBER)) {
            log.warn("User {} is not a barber", email);
            throw new UserRoleException("User " + email + " is not a BARBER");
        }

        barbershop.addBarber(barber);
        barbershopService.updateBarbershop(barbershop);
        userRepository.save(barber);
        log.info("Successfully added barber {} to barbershop {}",
                barber.getEmail(),
                barbershop.getAddress()
        );
    }

    public User getBarber(String email) {
        User barber = userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.info("Error while searching barber {}", email);
                    return new UserNotFoundException(email);
                }
        );

        if (!barber.getRoles().contains(Role.ROLE_BARBER)) {
            log.info("User {} is not a barber", email);
            throw new UserRoleException("User " + email + " is not a BARBER");
        }

        return barber;
    }

    @Transactional
    public void addServiceToBarber(String token, AddServiceToBarberRequest request) {
        String email = jwtService.extractUserName(token);
        User barber = getBarber(email);
        Barbershop barbershop = barber.getBarbershop();

        if (barbershop == null) {
            log.info("Can not add service to barber {} because barber has no barbershop", email);
            throw new BarberException("Barber has no barbershop");
        }

        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName())
                .orElseThrow(() -> {
                    log.info("No such service {}", request.serviceName());
                    return new BarberException("No such service: " + request.serviceName());
                });

        if (!barbershop.getServices().contains(service)) {
            log.info("No such service {} in barbershop {}", service.getName(), barbershop.getAddress());
            throw new BarbershopException("No such service in barbershop: " + request.serviceName());
        }

        barber.addService(service);
        userRepository.save(barber);
        serviceRepository.save(service);
        log.info("Successfully added service {} to barber {}",
                service.getName(), barber.getEmail());
    }

    @Transactional
    public void deleteBarbersService(String token, DeleteBarbersServiceRequest request) {
        String email = jwtService.extractUserName(token);
        User barber = getBarber(email);
        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName())
                .orElseThrow(() -> {
                    log.info("No such service {}", request.serviceName());
                    return new BarberException("No such service: " + request.serviceName());
                });

        if (!barber.getServices().contains(service)) {
            log.info("Barber {} has no such service {}",
                    barber.getEmail(), service.getName());

            throw  new BarberException("Barber has no such service: " + request.serviceName());
        }

        barber.deleteService(service);
        userRepository.save(barber);
        serviceRepository.save(service);
        log.info("Service {} was successfully deleted from barber {}",
                service.getName(), barber.getEmail());
    }

    @Transactional
    public void updateScheduleRequest(String token, UpdateScheduleListRequest requests) {
        for (var request : requests.requests()) updateScheduleRequest(token, request);
    }

    @Transactional
    public void updateScheduleRequest(String token, UpdateScheduleRequest request) {
        String email = jwtService.extractUserName(token);
        validateTime(request.from(), request.to());

        LocalDate curDate = DateUtils.getClosestDateByDayOfWeek(request.dayOfWeek());
        User barber = getBarber(email);

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
    public void deleteSchedule(String token, DeleteScheduleRequest request) {
        String email = jwtService.extractUserName(token);
        validateTime(request.from(), request.to());

        LocalDate curDate = DateUtils.getClosestDateByDayOfWeek(request.dayOfWeek());
        User barber = getBarber(email);

        for (int i = 0; i < 4; i++) {
            scheduleRepository.deleteByBarberIdAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTime(
                    barber.getId(), curDate, request.from(), request.to()
            );
        }

        log.info("Successfully deleted schedule: {}", request);
    }

    @Transactional
    public void addVocation(String token, AddVocationRequest request) {
        String email = jwtService.extractUserName(token);
        validateTime(request.from(), request.to());

        LocalDate curDate = DateUtils.getClosestDateByDayOfWeek(request.dayOfWeek());
        User barber = getBarber(email);

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
            throw new DateTimeException("Invalid time format: to is less than from");
        }
        try {
            DateUtils.validateScheduleTime(to);
            DateUtils.validateScheduleTime(from);
        } catch (IllegalArgumentException e) {
            log.info("Invalid time format: {}", e.getMessage());
            throw new DateTimeException(e.getMessage());
        }
    }
}