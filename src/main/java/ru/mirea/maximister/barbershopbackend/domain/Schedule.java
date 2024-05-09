package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.time.OffsetTime;

@Entity
@Table(name="schedule")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@BatchSize(size = 20)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    @Column(columnDefinition = "TIME")
    private OffsetTime time;
    private Boolean status;
    private Long barberId;
}
