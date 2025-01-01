package com.example.hair_salon.dto;

import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {
    private int userId;
    private int serviceId;
    @Future
    private LocalDateTime date;
}
