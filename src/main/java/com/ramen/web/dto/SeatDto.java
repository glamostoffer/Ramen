package com.ramen.web.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SeatDto {
    // вместительность столика
    private byte capacity;
    // цена за минуту брони
    private float price;
}
