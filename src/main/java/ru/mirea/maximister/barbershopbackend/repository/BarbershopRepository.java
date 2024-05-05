package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.maximister.barbershopbackend.domain.Barbershop;
import ru.mirea.maximister.barbershopbackend.domain.User;

import java.util.List;
import java.util.Optional;

public interface BarbershopRepository extends JpaRepository<Barbershop, Long> {
    List<Barbershop> findBarbershopsByCity(String city);

    Optional<Barbershop> getBarbershopByCityAndStreetAndNumber(
            String city, String street, String number
    );

    @Query("SELECT b.barbers FROM Barbershop b WHERE b.id=:id")
    List<User> findAllBarbersByBarbershopId(@Param("id") Long id);
}
