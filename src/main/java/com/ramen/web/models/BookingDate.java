package com.ramen.web.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "dates")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class BookingDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // день бронирования
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) // yyyy-mm-dd
    private LocalDate date;

    // время начала бронирования
    private LocalTime startTime; //hh:mm

    private LocalTime endTime;

    @OneToOne
//    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne
//    @JoinColumn(name="seat_id")
    private Seat seat;
}
