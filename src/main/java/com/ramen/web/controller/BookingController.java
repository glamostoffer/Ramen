package com.ramen.web.controller;

import com.ramen.web.models.BookingDate;
import com.ramen.web.models.UserEntity;
import com.ramen.web.repository.UserRepository;
import com.ramen.web.security.SecurityUtil;
import com.ramen.web.service.impl.BookingService;
import com.ramen.web.service.impl.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final SeatService seatService;
    private final UserRepository userRepository;
    private UserRepository userService;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public String getAllBookingDates(Model model) {
        model.addAttribute("books", bookingService.getAllBookingDates());
        model.addAttribute("seats", seatService.getAllSeats());
        UserEntity user = new UserEntity();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        return "books";
    }

    @PostMapping(value="/seat/{userId}")
    public String bookingSeat(
            @PathVariable Long userId,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam Long seatId){
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFormatted = LocalDate.parse(date, formatter1);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime timeFormatted = LocalTime.parse(time, formatter2);
        bookingService.createBooking(dateFormatted, timeFormatted, userId, seatId);
        return "redirect:/booking";
    }

    @GetMapping(value="/{date}")
    public ResponseEntity<List<BookingDate>> getAllBookingDates(@PathVariable LocalDate date) {
        return new ResponseEntity<>(bookingService.getAllBookingDatesByDate(date), HttpStatus.OK);
    }
}
