package ru.mirea.maximister.barbershopbackend.scheduledTasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.repository.ScheduleRepository;

@EnableScheduling
@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleUpdater {
    @Value("#{@'app.scheduler-ru.mirea.maximister.barbershopbackend.configuration.SchedulersConfiguration'.enable}")
    private boolean isSchedulingEnabled;
    @Value("#{@'app.scheduler-ru.mirea.maximister.barbershopbackend.configuration.SchedulersConfiguration'.workerScheduleUpdater.enable}")
    private boolean isCurrentSchedulerEnabled;

    private final ScheduleRepository scheduleRepository;

    @Scheduled(fixedDelayString = "#{@'app.scheduler-ru.mirea.maximister.barbershopbackend.configuration.SchedulersConfiguration'.workerScheduleUpdater.interval}")
    @Transactional
    public void updateSchedule() {
        if (!isSchedulingEnabled) {
            log.warn("Scheduling is not enabled");
            return;
        }
        if (!isCurrentSchedulerEnabled) {
            log.warn("Scheduling for schedule updater is not enabled");
            return;
        }

        scheduleRepository.updateStatusAndDateForOldSchedules();
        log.info("Successfully updated schedule");
    }
}
