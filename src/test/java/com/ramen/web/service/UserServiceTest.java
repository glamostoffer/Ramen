package com.ramen.web.service;

import com.ramen.web.models.Meal;
import com.ramen.web.models.Role;
import com.ramen.web.models.UserEntity;
import com.ramen.web.repository.MealRepository;
import com.ramen.web.repository.UserRepository;
import com.ramen.web.service.impl.MealService;
import com.ramen.web.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UserService_FindByEmail_ReturnUser() {
        String email = "user@mail.com";
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("user@mail.com")
                .username("user")
                .password("qwer")
                .roles(new ArrayList<>())
                .build();
        when(userRepository.findByEmail(email)).thenReturn(user);
        UserEntity userReturn = userService.findByEmail(email);
        Assertions.assertThat(userReturn).isNotNull();
    }

    @Test
    public void UserService_FindByUsername_ReturnUser() {
        String username = "user";
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("user@mail.com")
                .username("user")
                .password("qwer")
                .roles(new ArrayList<>())
                .build();
        when(userRepository.findByUsername(username)).thenReturn(user);
        UserEntity userReturn = userService.findByUsername(username);
        Assertions.assertThat(userReturn).isNotNull();
    }
}
