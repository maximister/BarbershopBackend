package ru.mirea.maximister.barbershopbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.maximister.barbershopbackend.domain.User;
import ru.mirea.maximister.barbershopbackend.domain.enums.Role;
import ru.mirea.maximister.barbershopbackend.dto.mappers.UserToDtoMapper;
import ru.mirea.maximister.barbershopbackend.dto.users.requests.DeleteUserRequest;
import ru.mirea.maximister.barbershopbackend.dto.users.responses.UserList;
import ru.mirea.maximister.barbershopbackend.exceptions.UserNotFoundException;
import ru.mirea.maximister.barbershopbackend.repository.ScheduleRepository;
import ru.mirea.maximister.barbershopbackend.repository.UserRepository;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserToDtoMapper userMapper;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Transactional
    public void deleteUser(DeleteUserRequest request)  {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UserNotFoundException(request.email())
        );

        if (user.getRoles().contains(Role.ROLE_BARBER)) {
            scheduleRepository.deleteAllByBarberId(user.getId());
        }

        log.info("User {} was deleted", user.getEmail());
        userRepository.deleteById(user.getId());

    }

    @Transactional
    public UserList getAllBarbers() {
        return new UserList(userRepository.findAllByRolesContains(Role.ROLE_BARBER).stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public UserList getAllClients() {
        return new UserList(userRepository.findAllByRolesContains(Role.ROLE_USER).stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public UserList getAllAdmins() {
        return new UserList(userRepository.findAllByRolesContains(Role.ROLE_ADMIN).stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public UserList getAllActiveBarbers() {
        return new UserList(userRepository.findAllByActiveAndRolesContains(true, Role.ROLE_BARBER)
                .stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public UserList getAllActiveClients() {
        return new UserList(userRepository.findAllByActiveAndRolesContains(true, Role.ROLE_USER)
                .stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public UserList getAllNonActiveBarbers() {
        return new UserList(userRepository.findAllByActiveAndRolesContains(false, Role.ROLE_BARBER)
                .stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public UserList getAllANonActiveClients() {
        return new UserList(userRepository.findAllByActiveAndRolesContains(false, Role.ROLE_USER)
                .stream()
                .map(userMapper::userToUSerDto)
                .collect(Collectors.toList()));
    }
}
