package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.maximister.barbershopbackend.domain.enums.ReservationStatus;

import java.sql.Time;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Date;

@Entity
@Table(name="reservation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private OffsetTime time;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private Long serviceId;
    private Long barberId;
    private Long clientId;
    private Long barbershopId;
}
