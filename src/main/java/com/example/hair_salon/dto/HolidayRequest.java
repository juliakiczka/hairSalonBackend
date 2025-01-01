package com.example.hair_salon.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HolidayRequest {
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
}
