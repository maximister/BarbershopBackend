package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.mirea.maximister.barbershopbackend.domain.enums.ReservationStatus;

import java.sql.Time;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Date;

@Entity
@Table(name="reservation")
@Getter
@Setter
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
