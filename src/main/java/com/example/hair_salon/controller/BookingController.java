package com.example.hair_salon.controller;

import com.example.hair_salon.dto.BookingRequest;
import com.example.hair_salon.entity.Booking;
import com.example.hair_salon.entity.Service;
import com.example.hair_salon.repository.BookingRepository;
import com.example.hair_salon.repository.ServiceRepository;
import com.example.hair_salon.repository.UserRepository;
import com.example.hair_salon.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Rezerwacje", description = "Endpoint'y do zarządzania rezerwacjami salonu.")
@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Pobierz wszystkie rezerwacje", description = "Zwraca listę wszystkich rezerwacji w systemie.")
    @GetMapping("/")
    public ResponseEntity<List<Map<String, Object>>> getBookings() {
        List<Map<String, Object>> bookingsWithDetails = bookingRepository.findAll().stream().map(booking -> {
            Map<String, Object> bookingDetails = new HashMap<>();
            bookingDetails.put("userName", booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
            bookingDetails.put("date", booking.getDate());
            bookingDetails.put("serviceName", booking.getService().getServiceName());
            return bookingDetails;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(bookingsWithDetails);
    }
    @Operation(summary = "Pobierz szczegóły rezerwacji", description = "Zwraca szczegóły rezerwacji na podstawie podanego ID.")
    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable int id) {
        return bookingService.getBookingById(id);
    }

    @Operation(summary = "Utwórz nową rezerwację", description = "Dodaje nową rezerwację na podstawie danych przesłanych w żądaniu.")
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequest) {
        // Sprawdź, czy użytkownik i usługa istnieją
        if (!userRepository.existsById(bookingRequest.getUserId())) {
            return ResponseEntity.badRequest().body("User not found");
        }
        Service service = serviceRepository.findById(bookingRequest.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Utwórz nową rezerwację
        Booking booking = new Booking();
        booking.setUserId(bookingRequest.getUserId());
        booking.setService(service);

        // Ustaw datę wizyty podaną przez użytkownika
        if (bookingRequest.getDate() == null || bookingRequest.getDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid date");
        }
        booking.setDate(bookingRequest.getDate());

        // Zapisz rezerwację w bazie danych
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking created successfully");
    }

    @Operation(summary = "Usuń rezerwację", description = "Usuwa rezerwację na podstawie podanego ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {
        boolean isDeleted = bookingService.deleteBookingById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Rezerwacja została pomyślnie usunięta.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
