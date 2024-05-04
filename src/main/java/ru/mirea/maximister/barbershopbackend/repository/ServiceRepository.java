package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.maximister.barbershopbackend.domain.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
