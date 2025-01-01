package com.example.hair_salon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private int userId;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "serviceId", referencedColumnName = "serviceId")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    // Metoda pomocnicza do pobierania nazwy usługi
    public String getServiceType() {
        return service != null ? service.getServiceName() : null;
    }
}
