package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.maximister.barbershopbackend.domain.Service;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);
    @Modifying
    @Query("DELETE FROM Barbershop b WHERE :serviceId IN (SELECT s.id FROM b.services s)")
    void deleteServiceFromBarbershops(@Param("serviceId") Long serviceId);

    @Modifying
    @Query("DELETE FROM User u WHERE :serviceId IN (SELECT s.id FROM u.services s)")
    void deleteServiceFromUsers(@Param("serviceId") Long serviceId);

}
