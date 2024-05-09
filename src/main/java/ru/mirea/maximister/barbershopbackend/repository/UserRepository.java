package ru.mirea.maximister.barbershopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFullname(String name);

    @Query("update User u set u.active = :isActive WHERE u.id = :userId")
    @Modifying
    void setUserIsActive(@Param("userId") Long id, @Param("isActive") boolean isActive);

    @Query("update User u set u.password = :password WHERE u.id = :userId")
    @Modifying
    void setUserPassword(@Param("userId") Long id, @Param("password") String password);
    List<User> findAllByRolesContains(Role role);

    List<User> findAllByActiveAndRolesContains(boolean active, Role role);
}
