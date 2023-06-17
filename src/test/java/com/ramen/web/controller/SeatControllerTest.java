package com.ramen.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramen.web.dto.MealDto;
import com.ramen.web.dto.SeatDto;
import com.ramen.web.models.Meal;
import com.ramen.web.models.Seat;
import com.ramen.web.service.impl.MealService;
import com.ramen.web.service.impl.SeatService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class SeatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService seatService;

    @Autowired
    private ObjectMapper objectMapper;
    private Seat seat;
    private SeatDto seatDto;

    @BeforeEach
    public void init() {
        seat = Seat.builder().capacity((byte)4).price(2.49F).build();
        seatDto = SeatDto.builder().capacity((byte)4).price(2.49F).build();
    }

    @Test
    public void MealController_CreateMeal_ReturnResponse() throws Exception {
        given(seatService.createSeat(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/seat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seatDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", CoreMatchers.is((int)seatDto.getCapacity())));
    }
}
