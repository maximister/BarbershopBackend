package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetTime;

@Entity
@Table(name="schedule")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @Column(columnDefinition = "TIME")
    private OffsetTime time;
    private Boolean status;
    private Long barberId;
}
