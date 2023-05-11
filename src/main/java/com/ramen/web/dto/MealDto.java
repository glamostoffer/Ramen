package com.ramen.web.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MealDto {
    private String name;
    private Float price;
    private String description;
}
