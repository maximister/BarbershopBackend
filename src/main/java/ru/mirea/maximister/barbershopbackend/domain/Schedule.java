package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name="schedule")
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime date;
    private Boolean status;
    private Long barberId;

    //TODO: время не должно превышать время работы салона!!
}
