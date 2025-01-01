package com.example.hair_salon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holidayId;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;
}