package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.Schedule;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //поиск свободных окон
    List<Schedule> findByBarberIdAndStatus(Long id, Boolean status);

    List<Schedule> findByDateLessThan(LocalDate date);

    List<Schedule> findByBarberIdAndStatusAndDateAndTimeGreaterThanEqualOrderByTime(
            Long barberId, Boolean status, LocalDate date, OffsetTime time
    );

    List<Schedule> findByBarberIdAndDateAndTimeGreaterThanEqualOrderByTime(
            Long barberId, LocalDate date, OffsetTime time
    );

    List<Schedule> findByBarberIdAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTime(
            Long barberId, LocalDate date, OffsetTime start, OffsetTime end
    );
    List<Schedule> findByBarberIdAndStatusAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTime(
            Long barberId, boolean status, LocalDate date, OffsetTime start, OffsetTime end
    );

    void deleteByBarberIdAndDateAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByTime(
            Long barberId, LocalDate date, OffsetTime start, OffsetTime end
    );

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.status = :status WHERE s.barberId = :barberId " +
            "AND s.date = :date AND s.time >= :startTime AND s.time <= :endTime")
    void updateStatusByBarberIdAndDateAndTimeRange(
            @Param("status") Boolean status, @Param("barberId") Long barberId,
            @Param("date") LocalDate date, @Param("startTime") OffsetTime startTime,
            @Param("endTime") OffsetTime endTime
    );

    @Modifying
    @Query(value = "UPDATE schedule" +
            "SET status = true, date = date + interval '28 days'" +
            "WHERE date < current_date;",
    nativeQuery = true)
    void updateStatusAndDateForOldSchedules();

    List<Schedule> findByBarberIdAndStatus(Long id, boolean status);

    void deleteAllByBarberId(Long id);
}
