package com.ramen.web.dto;

import com.ramen.web.models.UserEntity;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookingDto {
    private int id;

    private Date date;

    private UserEntity userEntity;
}
