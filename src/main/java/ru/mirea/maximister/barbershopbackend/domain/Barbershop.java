package ru.mirea.maximister.barbershopbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Barbershop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barbershop_id")
    private Long id;
    private String name;
    private String description;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private double longitude;
    private double latitude;

    private OffsetTime openTime;
    private OffsetTime closeTime;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "barbershop_service",
            joinColumns = @JoinColumn(name = "barbershop_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @OneToMany
    @JoinColumn(name = "barber_id")
    private Set<User> barbers = new HashSet<>();

    public void addBarber(User user) {
        user.setBarbershop(this);
        barbers.add(user);
    }

    @PrePersist
    private void init() {
        this.openTime = OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC);
        this.closeTime = OffsetTime.of(22, 0, 0, 0, ZoneOffset.UTC);
    }
}