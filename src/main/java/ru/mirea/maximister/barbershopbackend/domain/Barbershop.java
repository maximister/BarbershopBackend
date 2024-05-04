package ru.mirea.maximister.barbershopbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Barbershop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private double longitude;
    private double latitude;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "barbershop_service",
            joinColumns = @JoinColumn(name = "barbershop_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;
}
