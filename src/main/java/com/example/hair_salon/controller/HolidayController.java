package com.example.hair_salon.controller;

import com.example.hair_salon.dto.HolidayRequest;
import com.example.hair_salon.entity.Holiday;
import com.example.hair_salon.entity.User;
import com.example.hair_salon.repository.HolidayRepository;
import com.example.hair_salon.repository.UserRepository;
import com.example.hair_salon.service.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@Tag(name = "Urlopy", description = "Endpoint'y do zarządzania urlopami pracowników salonu.")
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private HolidayService holidayService;

    // Pobieranie wszystkich urlopów, w tym pełnych danych użytkownika
    @Operation(summary = "Pobierz wszystkie urlopy", description = "Zwraca listę wszystkich urlopów w systemie.")
    @GetMapping("/")
    public List<Holiday> getHolidays() {
        List<Holiday> holidays = holidayService.getAllHolidays();
        // Łączenie danych użytkownika z urlopem
        for (Holiday holiday : holidays) {
            User user = userRepository.findById(holiday.getUserId()).orElse(null);
            if (user != null) {
                holiday.setUser(user); // Ustawiamy pełne dane użytkownika w obiekcie Holiday
            }
        }
        return holidays;
    }

    // Pobieranie urlopu na podstawie ID
    @Operation(summary = "Pobierz szczegóły urlopu", description = "Zwraca szczegóły urlopu na podstawie podanego ID.")
    @GetMapping("/{id}")
    public Holiday getHoliday(@PathVariable int id) {
        Holiday holiday = holidayService.getHolidayById(id);
        User user = userRepository.findById(holiday.getUserId()).orElse(null);
        if (user != null) {
            holiday.setUser(user); // Ustawiamy pełne dane użytkownika
        }
        return holiday;
    }

    // Tworzenie nowego urlopu
    @Operation(summary = "Utwórz nowy urlop", description = "Dodaje nowy urlop na podstawie danych przesłanych w żądaniu.")
    @PostMapping
    public ResponseEntity<String> createHoliday(@RequestBody HolidayRequest holidayRequest) {
        // Sprawdź, czy użytkownik o podanym userId istnieje
        if (!userRepository.existsById(holidayRequest.getUserId())) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Utwórz nową encję Holiday z otrzymanych danych
        Holiday holiday = new Holiday();
        holiday.setUserId(holidayRequest.getUserId());
        holiday.setStartDate(holidayRequest.getStartDate());
        holiday.setEndDate(holidayRequest.getEndDate());
        holiday.setLeaveType(holidayRequest.getLeaveType());

        // Zapisz w bazie danych
        holidayRepository.save(holiday);

        return ResponseEntity.ok("Holiday created successfully");
    }

    // Usuwanie urlopu
    @Operation(summary = "Usuń urlop", description = "Usuwa urlop na podstawie podanego ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHoliday(@PathVariable int id) {
        boolean isDeleted = holidayService.deleteHolidayById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Urlop został pomyślnie usunięty.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
