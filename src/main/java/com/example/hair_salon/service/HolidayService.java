package com.example.hair_salon.service;

import com.example.hair_salon.entity.Holiday;
import com.example.hair_salon.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public Holiday getHolidayById(final int id) {
        return holidayRepository.findById(id).orElse(null);
    }

    public Holiday saveHoliday(final Holiday holiday) {
        return holidayRepository.save(holiday);
    }
    public boolean deleteHolidayById(final int id) {
        if (holidayRepository.existsById(id)) {
            holidayRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
