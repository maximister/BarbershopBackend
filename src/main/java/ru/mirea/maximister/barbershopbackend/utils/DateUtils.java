package ru.mirea.maximister.barbershopbackend.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.mirea.maximister.barbershopbackend.dto.service.requests.AddServiceRequest;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@UtilityClass
@Slf4j
public class DateUtils {
    private static final long SCHEDULE_UNIT = 15;

    public static LocalDate getClosestDateByDayOfWeek(int dayOfWeek) {
        TemporalAdjuster adjustToNexOrSameDay =
                TemporalAdjusters.nextOrSame(DayOfWeek.of(dayOfWeek));

        return LocalDate.now().with(adjustToNexOrSameDay);
    }

    public static void validateServiceDuration(Duration duration) throws IllegalArgumentException {
        if (duration.isNegative()) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (duration.toMinutes() % SCHEDULE_UNIT != 0) {
            throw new IllegalArgumentException("The duration should be a multiple of " + SCHEDULE_UNIT);
        }
    }

    public static void validateScheduleTime(OffsetTime time) throws IllegalArgumentException {
        if (time.getMinute() % SCHEDULE_UNIT != 0) {
            throw new IllegalArgumentException("Time minutes should be a multiple of " + SCHEDULE_UNIT);
        }
    }
}
