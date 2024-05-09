package ru.mirea.maximister.barbershopbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.mirea.maximister.barbershopbackend.configuration.SchedulersConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({SchedulersConfiguration.class})
public class BarbershopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarbershopBackendApplication.class, args);
    }
}
