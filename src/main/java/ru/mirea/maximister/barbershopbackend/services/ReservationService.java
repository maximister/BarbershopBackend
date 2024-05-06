package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Reservation;
import ru.mirea.maximister.barbershopbackend.domain.Schedule;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.ReservationStatus;
import ru.mirea.maximister.barbershopbackend.dto.reservation.AddReservationRequest;
import ru.mirea.maximister.barbershopbackend.repository.ReservationRepository;
import ru.mirea.maximister.barbershopbackend.repository.ScheduleRepository;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

import java.time.OffsetTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ServiceRepository serviceRepository;
    private final BarberService barberService;


    /**
     * олучение записей работника/клиента
     * получение свободных окон работника
     * получение всех доступных окон в формате окно - мастер
     * получение списка доступных слотов на запись с учетом длительности процедуры
     * создание записи +
     * отмена записи (смена статуса) (не забыть освободить расписание)
     * мб перенос записи
     */

    //TODO: продумать как закрывать записи

    @Transactional
    public void AddReservation(AddReservationRequest request) {
        User barber = barberService.getBarber(request.barberEmail());
        User client = userRepository.findByEmail(request.clientEmail()).orElse(null);
        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findByName(request.serviceName()).orElseThrow(
                IllegalArgumentException::new
        );

        if (!barber.getServices().contains(service)) {
            //TODO: custom error
            log.info("Barber {} has no {} service", barber.getEmail(), service.getName());
            throw new IllegalArgumentException();
        }

        Reservation reservation = Reservation.builder()
                .date(request.date())
                .time(request.time())
                .status(ReservationStatus.ACTIVE)
                .serviceId(service.getId())
                .barberId(barber.getId())
                .clientId(client.getId())
                .barbershopId(barber.getBarbershop().getId())
                .build();

        OffsetTime end = reservation.getTime().plus(service.getDuration());

        List<Schedule> scheduleList = scheduleRepository
                .findByBarberIdAndStatusAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTime(
                        reservation.getBarberId(), true,
                        reservation.getDate(), reservation.getTime(), end
                );

        OffsetTime ptr = reservation.getTime();
        for (Schedule scheduleUnit : scheduleList) {
            if (ptr != scheduleUnit.getTime()) {
                log.info("error during creating reservation {}: barber has no space in schedule",
                        request
                );
                //TODO: кастомная ошибка составления расписания
                throw new IllegalArgumentException();
            }

            scheduleUnit.setStatus(false);
            scheduleRepository.save(scheduleUnit);
            ptr = ptr.plusMinutes(15);
            if (ptr.isAfter(end)) {
                break;
            }
        }

        reservationRepository.save(reservation);
        log.info("Successfully added reservation {}", reservation);
    }

    @Transactional
    public void cancelReservation() {

    }

    @Transactional
    public void getClientsReservations() {

    }

    @Transactional
    public void getBarbersReservations() {

    }

    @Transactional
    public void getBarbersSlots() {

    }

    @Transactional
    public void getAllSlots() {
        //все доступные слоты (у любого барбера, с учетом длительности процедуры)
    }
}
