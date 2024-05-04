package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.maximister.barbershopbackend.domain.Schedule;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //поиск свободных окон
    List<Schedule> findByBarberIdAndStatus(Long id, Boolean status);

    List<Schedule> findByDateLessThan(Date date);

    List<Schedule> findByBarberIdAndStatusAndDateAndTimeGreaterThanEqualOrderByTime
            (
                    Long barberId, Boolean status, Date date, Time time
            );

    List<Schedule> findByBarberId(Long id);
}
