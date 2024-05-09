package ru.mirea.maximister.barbershopbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="service")
@Getter
@Setter
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
    @JsonIgnore
    private Set<User> barbers = new HashSet<>();

    @ManyToMany(mappedBy = "services",
            fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })

    @JsonIgnore
    private Set<Barbershop> barbershops = new HashSet<>();
}
