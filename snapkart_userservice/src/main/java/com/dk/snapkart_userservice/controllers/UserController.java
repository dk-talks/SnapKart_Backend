package com.dk.snapkart_userservice.controllers;

import com.dk.snapkart_userservice.config.JwtConfig;
import com.dk.snapkart_userservice.dtos.LoginRequestDTO;
import com.dk.snapkart_userservice.dtos.LoginResponseDTO;
import com.dk.snapkart_userservice.dtos.SignupResponseDTO;
import com.dk.snapkart_userservice.dtos.SignupRquestDTO;
import com.dk.snapkart_userservice.models.Session;
import com.dk.snapkart_userservice.models.User;
import com.dk.snapkart_userservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;
    private JwtConfig jwtConfig;

    public UserController(UserService userService, JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/signup")
    public SignupResponseDTO signUp(
            @RequestBody SignupRquestDTO signupRquestDTO
    ) {
        User user = userService.signup(
                signupRquestDTO.getName(),
                signupRquestDTO.getEmail(),
                signupRquestDTO.getPassword()
        );

        // if user already present, then it would have already thrown
        if(user == null) {
            return null;
        }

        SignupResponseDTO responseDTO = new SignupResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setName(user.getName());

        return responseDTO;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        Session session = userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(session.getTokenValue());

        return responseDTO;
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Boolean isVerified = jwtConfig.validateToken(token);

        if(isVerified) {
            return new ResponseEntity<>(isVerified, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(isVerified, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtConfig.extractUserId(token);

        userService.logout(userId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
