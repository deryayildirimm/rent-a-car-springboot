package dev.patika.definexjavaspringbootbootcamp2025.hw3.controller;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.User;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchUserException;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.UserService;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.list());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable String id) {
        return handleExceptions(() -> {
            UUID uuid = UUID.fromString(id);
            return ResponseEntity.ok(userService.getUserProfile(uuid));
        });
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return handleExceptions(() -> {
            UUID uuid = UUID.fromString(id);
            user.setId(uuid);
            return ResponseEntity.ok(userService.update(user));
        });
    }

    private ResponseEntity<User> handleExceptions(Supplier<ResponseEntity<User>> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchUserException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
