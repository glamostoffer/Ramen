package com.ramen.web.service;

import com.ramen.web.dto.RegistrationDto;
import com.ramen.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
