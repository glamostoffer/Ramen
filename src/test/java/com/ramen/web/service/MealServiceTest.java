package com.ramen.web.service;

import com.ramen.web.dto.MealDto;
import com.ramen.web.models.Meal;
import com.ramen.web.repository.MealRepository;
import com.ramen.web.service.impl.MealService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {
    @Mock
    private MealRepository mealRepository;
    @InjectMocks
    private MealService mealService;

    @Test
    public void MealService_CreateMeal_ReturnsMealDto() {
        Meal meal = Meal.builder()
                .name("Ramen")
                .description("Tasty ramen!")
                .price(2.49F)
                .build();
        MealDto mealDto = MealDto.builder().name("Ramen").description("Tasty ramen!").price(2.49F).build();
        when(mealRepository.save(Mockito.any(Meal.class))).thenReturn(meal);
        MealDto savedMeal = mealService.createMeal(mealDto);
        Assertions.assertThat(savedMeal).isNotNull();
    }

    @Test
    public void MealService_FindById_ReturnMeal() {
        Long mealId = 1L;
        Meal meal = Meal.builder()
                .id(1L)
                .name("Ramen")
                .description("Tasty ramen!")
                .price(2.49F).build();
        when(mealRepository.findById(mealId)).thenReturn(Optional.ofNullable(meal));
        Meal mealReturn = mealService.getMealById(mealId);
        Assertions.assertThat(mealReturn).isNotNull();
    }
}
