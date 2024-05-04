package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.maximister.barbershopbackend.domain.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByBarberId(Long barberId);
    List<Reservation> findByDateBetween(Date start, Date end);
    List<Record> findByStatusAndDateAndTimeLessThan(String status, Date date, Date time);
}
