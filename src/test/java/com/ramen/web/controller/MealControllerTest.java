package com.ramen.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramen.web.dto.MealDto;
import com.ramen.web.models.Meal;
import com.ramen.web.service.impl.MealService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.notNullValue;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class MealControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MealService mealService;

    @Autowired
    private ObjectMapper objectMapper;
    private Meal meal;
    private MealDto mealDto;

    @BeforeEach
    public void init() {
        meal = Meal.builder().name("Sushi").price(2.49F).description("Tasty!").build();
        mealDto = MealDto.builder().name("Sushi").price(2.49F).description("Tasty!").build();
    }

    @Test
    public void MealController_GetAll_ReturnString() throws Exception {
        this.mockMvc.perform(get("/meal"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    public void MealController_CreateMeal_ReturnResponse() throws Exception {
        given(mealService.createMeal(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/meal/admin/create-meal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mealDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(mealDto.getName())));
    }
}
