package com.ramen.web.controller;

import com.ramen.web.dto.SeatDto;
import com.ramen.web.models.Seat;
import com.ramen.web.service.impl.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seat")
@AllArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {
        return new ResponseEntity<>(seatService.getAllSeats(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SeatDto> createSeat(@RequestBody SeatDto seatDto) {
        return new ResponseEntity<>(seatService.createSeat(seatDto), HttpStatus.CREATED);
    }
}
