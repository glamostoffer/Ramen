package com.ramen.web.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float price;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private Meal meal;
}
