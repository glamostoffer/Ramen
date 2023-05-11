package com.ramen.web.service.impl;

import com.ramen.web.models.BookingDate;
import com.ramen.web.repository.BookingDateRepository;
import com.ramen.web.repository.SeatRepository;
import com.ramen.web.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingDateRepository bookingDateRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    public BookingDate createBooking(LocalDate date, LocalTime time, Long userId, Long seatId) {
        BookingDate booking = new BookingDate();
        booking.setDate(date);
        booking.setStartTime(time);
        // можно сделать выбор продолжительности
        booking.setEndTime(time.plusHours(1));
        booking.setSeat(seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("cannot find seat")));
        booking.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("cannot find userEntity")));

        if (checkBooking(date, time, seatId)) {
            bookingDateRepository.save(booking);
            return booking;
        }
        return null; // проработать ошибку
    }

    public boolean checkBooking(LocalDate date, LocalTime time, Long seatId) {
        return bookingDateRepository.findAllByDateAndTimeAndSeat(date, time, time.plusHours(1), seatId).size() == 0;
    }

    public List<BookingDate> getAllBookingDatesByDate(LocalDate date) {
        return bookingDateRepository.findAllByDate(date);
    }

    public List<BookingDate> getAllBookingDates() {
        return bookingDateRepository.findAll();
    }

}
