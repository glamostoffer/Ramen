package com.ramen.web.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // вместительность столика
    private byte capacity;

    // цена бронирования
    private float price;

//    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
//    private List<BookingDate> bookingDate;
}
