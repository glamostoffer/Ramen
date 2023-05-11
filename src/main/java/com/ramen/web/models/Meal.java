package com.ramen.web.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "meals")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float price;

    private String description;
}
