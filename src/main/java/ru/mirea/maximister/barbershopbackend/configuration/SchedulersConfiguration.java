package ru.mirea.maximister.barbershopbackend.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app.scheduler", ignoreUnknownFields = false)
public record SchedulersConfiguration(
        @NotNull
        boolean enable,
        @NotNull
        WorkerScheduleUpdater workerScheduleUpdater,
        @NotNull
        MailSender mailSender
) {
    public record WorkerScheduleUpdater(boolean enable, @NotNull Duration interval) {
    }

    public record MailSender(boolean enable, @NotNull Duration interval) {
    }
}
