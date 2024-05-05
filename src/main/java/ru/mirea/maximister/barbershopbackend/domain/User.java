package ru.mirea.maximister.barbershopbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String fullname;
    private boolean isActive;
    private LocalDateTime dateOfCreation;

    @Column(length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();


    @ManyToMany(mappedBy = "barbers")
    private Set<Service> services = new HashSet<>();

    @ManyToOne
    private Barbershop barbershop;

    public void addService(Service service) {
        services.add(service);
        service.getBarbers().add(this);
    }
    public void deleteService(Service service) {
        services.remove(service);
        service.getBarbers().remove(this);
    }

    @PrePersist
    private void init() {
        dateOfCreation = LocalDateTime.now();
    }

    //Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
