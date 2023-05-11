package com.ramen.web.repository;

import com.ramen.web.models.BookingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingDateRepository extends JpaRepository<BookingDate, Long> {
    List<BookingDate> findAllByDate(LocalDate date);

    @Query(
            value = "select b from BookingDate b " +
                    "where b.seat.id = :seatId " +
                    "and b.date = :date " +
                    "and ((b.endTime > :startTime and b.endTime < :endTime) " +
                    "or (b.startTime >= :startTime and b.startTime < :endTime))"
    )
    List<BookingDate> findAllByDateAndTimeAndSeat(LocalDate date, LocalTime startTime, LocalTime endTime, Long seatId);
}
