package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Date;

@Entity
@Table(name="reservation")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private OffsetTime time;
    private String status;
    private Long serviceId;
    private Long barberId;
    private Long clientId;
    private Long barbershopId;
}
