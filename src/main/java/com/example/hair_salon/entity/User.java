package com.example.hair_salon.entity;

import com.example.hair_salon.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    @Schema(description = "Rola użytkownika. Możliwe wartości: ADMIN, PRACOWNIK, KLIENT.", allowableValues = {"ADMIN", "PRACOWNIK", "KLIENT"})
    private Role role;
}
