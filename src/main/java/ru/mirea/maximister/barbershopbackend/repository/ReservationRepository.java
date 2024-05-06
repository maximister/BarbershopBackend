package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.maximister.barbershopbackend.domain.Reservation;
import ru.mirea.maximister.barbershopbackend.domain.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByClientIdAndStatus(Long clientId, ReservationStatus status);
    List<Reservation> findByBarberId(Long barberId);
    Optional<Reservation> findByBarberIdAndDateAndTime(Long barberId, LocalDate date, OffsetTime time);
    List<Reservation> findByBarberIdAndStatus(Long barberId, ReservationStatus status);
    List<Reservation> findByDateBetween(LocalDate start, LocalDate end);
    List<Reservation> findByStatusAndDateAndTimeLessThan(ReservationStatus status, LocalDate date, OffsetTime time);
}
