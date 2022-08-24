package com.example.movie.services;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.TokenDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.dtos.UserLoginDto;

public interface AuthService {
     UserDto register(CreateUserDto user);
     TokenDto login(UserLoginDto loginDTO);
}
