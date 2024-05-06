package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.domain.Reservation;
import ru.mirea.maximister.barbershopbackend.domain.Schedule;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.ReservationStatus;
import ru.mirea.maximister.barbershopbackend.dto.mappers.BarbershopToBarbershopResponseMapper;
import ru.mirea.maximister.barbershopbackend.dto.mappers.ServiceToServiceResponseMapper;
import ru.mirea.maximister.barbershopbackend.dto.mappers.UserToResponsesMapper;
import ru.mirea.maximister.barbershopbackend.dto.reservation.FreeBarbersSlots;
import ru.mirea.maximister.barbershopbackend.dto.reservation.FreeSlot;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.AddReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.DenyReservationRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.requests.GetSlotsInBarbershopRequest;
import ru.mirea.maximister.barbershopbackend.dto.reservation.responses.*;
import ru.mirea.maximister.barbershopbackend.repository.ReservationRepository;
import ru.mirea.maximister.barbershopbackend.repository.ScheduleRepository;
import ru.mirea.maximister.barbershopbackend.repository.ServiceRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

import java.time.OffsetTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ServiceRepository serviceRepository;
    private final BarberService barberService;
    private final BarbershopService barbershopService;
    private final UserToResponsesMapper userMapper;
    private final ServiceToServiceResponseMapper serviceMapper;
    private final BarbershopToBarbershopResponseMapper barbershopMapper;


    /**
     * получение записей работника/клиента
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
    public void denyReservation(DenyReservationRequest request) {
        User barber = barberService.getBarber(request.barberEmail());
        Reservation reservation = reservationRepository.findByBarberIdAndDateAndTime(
                barber.getId(), request.date(), request.time()
        ).orElseThrow(() -> {
            log.info("error during denying reservation {}: no such reservation", request);
            //TODO: кастомная ошибка составления расписания
            return new IllegalArgumentException();
        });

        reservation.setStatus(ReservationStatus.DENIED);

        //освобождаем расписание
        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findById(reservation.getServiceId()).orElseThrow(() -> {
            log.warn("Unexpected error during deleting reservation: no such service {}", reservation);
            //TODO: no such service ex
            return new RuntimeException();
        });

        OffsetTime end = reservation.getTime().plus(service.getDuration());
        scheduleRepository.updateStatusByBarberIdAndDateAndTimeRange(
                true, barber.getId(), reservation.getDate(), reservation.getTime(), end);

        reservationRepository.save(reservation);
        log.info("Successfully denied reservation {}", reservation);
    }

    @Transactional
    public ClientReservationList getClientsActiveReservations(String clientEmail) {
        User client = userRepository.findByEmail(clientEmail).orElse(null);
        List<Reservation> reservations = reservationRepository.findByClientIdAndStatus(
                client.getId(), ReservationStatus.ACTIVE
        );

        log.info("Created list of clients {} reservations", clientEmail);
        return new ClientReservationList(
                userMapper.userToClientResponse(client),
                reservations.stream()
                        .map(r -> toReservationWithoutClientInfoDtoMapper(
                                r,
                                userRepository.findById(r.getBarberId()).orElse(null),
                                serviceRepository.findById(r.getServiceId()).orElse(null))
                        )
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public BarberReservationsList getBarbersReservations(String barberEmail) {
        User barber = barberService.getBarber(barberEmail);
        List<Reservation> reservations = reservationRepository.findByBarberIdAndStatus(
                barber.getId(), ReservationStatus.ACTIVE
        );

        log.info("Created list of barbers {} reservations", barberEmail);
        return new BarberReservationsList(
                userMapper.userToBarberResponse(barber),
                reservations.stream()
                        .map(this::toReservationWithoutBarberInfoDto)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public BarberFreeSlotsResponse getBarbersSlots(String barberEmail) {
        User barber = barberService.getBarber(barberEmail);
        List<Schedule> slots = scheduleRepository
                .findByBarberIdAndStatus(barber.getId(), true);

        log.info("Created list of barbers {} slots", barberEmail);
        return new BarberFreeSlotsResponse(
                userMapper.userToBarberResponse(barber),
                slots.stream().map(s -> new FreeSlot(s.getDate(), s.getTime()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public SlotsInBarbershopResponse getAllSlotsInBarbershop(GetSlotsInBarbershopRequest request) {
        Barbershop barbershop = barbershopService.getBarbershopByAddress(
                request.city(), request.street(), request.number()
        );
        Set<User> barbers = barbershop.getBarbers();
        List<FreeBarbersSlots> slots = barbers.stream()
                .map(b -> new FreeBarbersSlots(
                        userMapper.userToBarberResponse(b),
                        scheduleRepository.findByBarberIdAndStatus(b.getId(), true)
                                .stream().map(s -> new FreeSlot(s.getDate(), s.getTime()))
                                .collect(Collectors.toList())
                ))
                .toList();

        log.info("Created list of barbershop {} slots", barbershop.getAddress());
        return new SlotsInBarbershopResponse(
                barbershopMapper.barbershopToBarbershopResponse(barbershop),
                slots
        );
    }

    private ReservationWithoutClientInfoDto toReservationWithoutClientInfoDtoMapper(
            Reservation reservation, User barber,
            ru.mirea.maximister.barbershopbackend.domain.Service service
    ) {
        return new ReservationWithoutClientInfoDto(
                reservation.getDate(),
                reservation.getTime(),
                serviceMapper.serviceToServiceResponse(service),
                userMapper.userToBarberResponse(barber),
                barbershopMapper.barbershopToBarbershopResponse(barber.getBarbershop())
        );
    }

    private ReservationWithoutBarberInfoDto toReservationWithoutBarberInfoDto(
            Reservation reservation
    ) {
        ru.mirea.maximister.barbershopbackend.domain.Service service
                = serviceRepository.findById(reservation.getServiceId()).orElse(null);
        User client = userRepository.findById(reservation.getClientId()).orElse(null);
        return new ReservationWithoutBarberInfoDto(
                reservation.getDate(),
                reservation.getTime(),
                serviceMapper.serviceToServiceResponse(service),
                userMapper.userToClientResponse(client)
        );
    }
}
