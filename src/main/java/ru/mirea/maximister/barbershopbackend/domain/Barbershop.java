package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "barbershop")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name = "barbershop_service",
            joinColumns = @JoinColumn(name = "barbershop_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services = new HashSet<>();

    @OneToMany
    private Set<User> barbers = new HashSet<>();

    public void addBarber(User user) {
        user.setBarbershop(this);
        barbers.add(user);
    }

    public void addService(Service service) {
        service.getBarbershops().add(this);
        services.add(service);
    }

    public void deleteService(Service service) {
        services.remove(service);
        service.getBarbershops().remove(this);
    }


    public String getAddress() {
        return "City: " + city + " Street: " + street + " Number: " + number;
    }

    @PrePersist
    private void init() {
        this.openTime = OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC);
        this.closeTime = OffsetTime.of(22, 0, 0, 0, ZoneOffset.UTC);
    }
}
