package com.ramen.web.repository;

import com.ramen.web.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findByName(String name);
    Meal findByPrice(Float price);
    boolean existsByName(String name);
    @Query(
            value="SELECT m FROM Meal m WHERE m.price <= :price"
    )
    List<Meal> findAllWithLowerPrice(Float price);

    @Query(
            value="SELECT m FROM Meal m WHERE m.price >= :price"
    )
    List<Meal> findAllWithHigherPrice(Float price);
}
