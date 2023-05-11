package com.ramen.web.repository;

import com.ramen.web.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query(
            value="SELECT s FROM Seat s WHERE s.capacity = :capacity"
    )
    Seat findByCapacity(byte capacity);
//    @Query(
//            value="SELECT s FROM Seat s WHERE s.bookingDate = :date and "
//    )
//    Seat findByBookingDate(Date date);
}
