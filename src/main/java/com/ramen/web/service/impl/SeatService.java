package com.ramen.web.service.impl;

import com.ramen.web.dto.SeatDto;
import com.ramen.web.models.Seat;
import com.ramen.web.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final BookingService bookingService;

    public SeatDto createSeat(SeatDto seatDTO) {
        Seat seat = new Seat();
        seat.setPrice(seatDTO.getPrice());
        seat.setCapacity(seatDTO.getCapacity());
        Seat newSeat = seatRepository.save(seat);

        SeatDto seatResponse = new SeatDto();
        seatResponse.setCapacity(newSeat.getCapacity());
        seatResponse.setPrice(newSeat.getPrice());
        return seatResponse;
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
}
