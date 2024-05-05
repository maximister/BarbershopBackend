package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.mappers.UserToResponsesMapper;
import ru.mirea.maximister.barbershopbackend.dto.users.requests.DeleteUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.AdminList;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.BarberList;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.ClientList;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserToResponsesMapper userToBarberResponseMapper;

    @Transactional
    public User findUserById(Long id) {
        //TODO: кастомная ошибка
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Transactional
    public void deleteUser(DeleteUserRequest request)  {
        //TODO: продумать так, чтобы удалять мог только сам пользователь или админ
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                //TODO: кастомная ошибка
                () -> new UsernameNotFoundException("")
        );
        log.info("User {} was deleted", user.getEmail());
        userRepository.deleteById(user.getId());
    }

    @Transactional
    public BarberList getAllBarbers() {
        return new BarberList(userRepository.findAllByRolesContains(Role.ROLE_BARBER).stream()
                .map(userToBarberResponseMapper::userToBarberResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public ClientList getAllClients() {
        return new ClientList(userRepository.findAllByRolesContains(Role.ROLE_USER).stream()
                .map(userToBarberResponseMapper::userToClientResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public AdminList getAllAdmins() {
        return new AdminList(userRepository.findAllByRolesContains(Role.ROLE_ADMIN).stream()
                .map(userToBarberResponseMapper::userToAdminResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public BarberList getAllActiveBarbers() {
        return new BarberList(userRepository.findAllByActiveAndRolesContains(true, Role.ROLE_BARBER)
                .stream()
                .map(userToBarberResponseMapper::userToBarberResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public ClientList getAllActiveClients() {
        return new ClientList(userRepository.findAllByActiveAndRolesContains(true, Role.ROLE_USER)
                .stream()
                .map(userToBarberResponseMapper::userToClientResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public BarberList getAllNonActiveBarbers() {
        return new BarberList(userRepository.findAllByActiveAndRolesContains(false, Role.ROLE_BARBER)
                .stream()
                .map(userToBarberResponseMapper::userToBarberResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public ClientList getAllANonActiveClients() {
        return new ClientList(userRepository.findAllByActiveAndRolesContains(false, Role.ROLE_USER)
                .stream()
                .map(userToBarberResponseMapper::userToClientResponse)
                .collect(Collectors.toList()));
    }
}
