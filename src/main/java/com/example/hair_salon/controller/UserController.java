package com.example.hair_salon.controller;

import com.example.hair_salon.entity.User;
import com.example.hair_salon.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Użytkownicy", description = "Endpoint'y do zarządzania użytkownikami salonu.")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Pobierz wszystkich użytkowników", description = "Zwraca listę wszystkich użytkowników zapisanych w systemie.")
    @GetMapping("/")
    public ResponseEntity<List<Map<String, Object>>> getUsers() {
        List<Map<String, Object>> usersWithDetails = userService.getAllUsers().stream().map(user -> {
            Map<String, Object> userDetails = Map.of(
                    "fullName", user.getFirstName() + " " + user.getLastName(),
                    "email", user.getEmail(),
                    "role", user.getRole().toString()
            );
            return userDetails;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(usersWithDetails);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz szczegóły użytkownika", description = "Zwraca dane użytkownika na podstawie podanego ID.")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @Operation(summary = "Dodaj nowego użytkownika", description = "Dodaje nowego użytkownika do systemu.")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        var savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń użytkownika", description = "Usuwa użytkownika na podstawie ID.")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Użytkownik o ID " + id + " został usunięty.");
        } else {
            return ResponseEntity.status(404).body("Użytkownik o ID " + id + " nie został znaleziony.");
        }
    }
}
