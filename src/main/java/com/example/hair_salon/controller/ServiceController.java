package com.example.hair_salon.controller;

import com.example.hair_salon.entity.Service;
import com.example.hair_salon.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Usługi", description = "Endpoint'y do zarządzania usługami salonu.")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/")
    @Operation(summary = "Pobierz wszystkie usługi", description = "Zwraca listę wszystkich dostępnych usług oferowanych przez salon.")
    public List<Service> getServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz szczegóły usługi", description = "Zwraca szczegółowe informacje o usłudze na podstawie podanego ID.")
    public Service getService(@PathVariable int id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping
    @Operation(summary = "Dodaj nową usługę", description = "Dodaje nową usługę do oferty salonu.")
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        var savedService = serviceService.saveService(service);
        return ResponseEntity.ok(savedService);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń usługę", description = "Usuwa usługę z oferty na podstawie podanego ID.")
    public ResponseEntity<String> deleteService(@PathVariable int id) {
        boolean isDeleted = serviceService.deleteServiceById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Usługa o ID " + id + " została usunięta.");
        } else {
            return ResponseEntity.status(404).body("Usługa o ID " + id + " nie została znaleziona.");
        }
    }
}
