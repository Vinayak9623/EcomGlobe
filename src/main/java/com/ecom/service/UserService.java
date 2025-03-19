package com.ecom.service;

import com.ecom.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> registerUser(UserDto userDto);
    ResponseEntity<?> authenticateUser(UserDto userDto);
}
