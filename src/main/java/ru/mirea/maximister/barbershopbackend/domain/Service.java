package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;

@Entity
@Table(name="service")
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private Duration duration;
}
