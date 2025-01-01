package com.example.hair_salon.service;

import com.example.hair_salon.entity.Booking;
import com.example.hair_salon.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(final int bookingId) {
        //"No booking found with given id."
        return bookingRepository.findById(bookingId).orElse(null);
    }

    public Booking saveBooking(final Booking booking) {
        return bookingRepository.save(booking);
    }

    public boolean deleteBookingById(final int id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
