package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.maximister.barbershopbackend.domain.Reservation;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByBarberId(Long barberId);
    List<Reservation> findByDateBetween(LocalDate start, LocalDate end);
    List<Reservation> findByStatusAndDateAndTimeLessThan(String status, LocalDate date, OffsetTime time);
}
