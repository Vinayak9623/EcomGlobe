package com.ecom.serviceImpl;
import com.ecom.JWTAuth.JWTUtil;
import com.ecom.Repository.UserRepository;
import com.ecom.customException.UserNotFoundException;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> registerUser(UserDto userDto) {

        if (userRepository.findUserByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public ResponseEntity<?> authenticateUser(UserDto userDto) {


        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}

