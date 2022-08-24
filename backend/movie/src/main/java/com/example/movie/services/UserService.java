package com.example.movie.services;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(long id) throws ResourceNotFoundException;

    UserDto findByName(String name) throws ResourceNotFoundException;

    UserDto save(CreateUserDto user);

    void update(long id, UserDto user, String authorizedUsername);

    void deleteByID(long id, String authorizedUsername);
}