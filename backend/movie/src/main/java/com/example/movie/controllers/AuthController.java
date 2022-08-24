package com.example.movie.controllers;

import com.example.movie.aspects.Audit;
import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.TokenDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.dtos.UserLoginDto;
import com.example.movie.services.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    @Audit
    UserDto register(@Valid @RequestBody CreateUserDto userRegistrationDTO) {
        return authServiceImpl.register(userRegistrationDTO);
    }

    @PostMapping("/login")
    public TokenDto login(@Valid @RequestBody UserLoginDto loginDTO) {
        return authServiceImpl.login(loginDTO);
    }
}
