package ru.mirea.maximister.barbershopbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.maximister.barbershopbackend.dto.users.requests.DeleteUserRequest;
import ru.mirea.maximister.barbershopbackend.services.UserService;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController {
    //дела админские с пользователями
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(
                userService.findUserById(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest request) {
        userService.deleteUser(request);
        return new ResponseEntity<>(
                "User was successfully deleted",
                HttpStatus.OK
        );
    }


    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdminsByActivity() {
        return new ResponseEntity<>(
                userService.getAllAdmins(),
                HttpStatus.OK
        );
    }

    //TODO: мб упростить clientlist, barberlist и adminlist в один лист
    @GetMapping("/clients")
    public ResponseEntity<?> getAllClientsByActivity(@RequestParam(required = false) Boolean isActive) {
        return new ResponseEntity<>(
                isActive == null ? userService.getAllClients() :
                        isActive ? userService.getAllActiveClients() :
                                userService.getAllANonActiveClients(),
                HttpStatus.OK
        );
    }

    @GetMapping("/barbers")
    public ResponseEntity<?> getAllBarbersByActivity(@RequestParam(required = false) Boolean isActive) {
        return new ResponseEntity<>(
                isActive == null ? userService.getAllBarbers() :
                        isActive ? userService.getAllActiveBarbers() :
                                userService.getAllNonActiveBarbers(),
                HttpStatus.OK
        );
    }

}
