package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;

public interface BarbershopRepository extends JpaRepository<Barbershop, Long> {
}
