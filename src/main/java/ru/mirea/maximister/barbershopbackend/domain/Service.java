package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="service")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private Integer price;
    private Duration duration;

    @ManyToMany
    private Set<User> barbers = new HashSet<>();

    @ManyToMany
    private Set<Barbershop> barbershops = new HashSet<>();
}
