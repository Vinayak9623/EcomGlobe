package com.ecom.service;
import com.ecom.JWTAuth.JWTUtil;
import com.ecom.Repository.UserRepository;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.service.UserService;
import com.ecom.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUsername("xyz");
        userDto.setEmail("xyz12@gmail.com");
        userDto.setPassword("password123");

        user = new User();
        user.setUsername("xyz");
        user.setEmail("xyz12@gmail.com");
        user.setPassword("password123");

    }

    @Test
    void testRegisterUser_AlreadyExist() {
        when(userRepository.findUserByUsername(userDto.getUsername())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.registerUser(userDto);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Username is already taken", response.getBody());
    }

    @Test
    void testAuthenticateUser_InvalidPassword() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userDto.getPassword(), user.getPassword())).thenReturn(false);

        ResponseEntity<?> response = userService.authenticateUser(userDto);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid credentials", response.getBody());
    }


    @Test
    void testAuthenticateUser_Success() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userDto.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("jwt_token");

        ResponseEntity<?> response = userService.authenticateUser(userDto);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(((Map<?,?>) response.getBody()).containsKey("token"));
        assertEquals("jwt_token", ((Map<?,?>) response.getBody()).get("token"));
    }
}

