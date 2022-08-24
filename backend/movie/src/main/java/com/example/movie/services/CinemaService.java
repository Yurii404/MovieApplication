package com.example.movie.services;

import com.example.movie.dtos.CinemaDto;
import com.example.movie.dtos.CreateCinemaDto;
import com.example.movie.exception.ResourceNotFoundException;

import java.util.List;

public interface CinemaService {
    List<CinemaDto> findAll();

    CinemaDto findById(long id) throws ResourceNotFoundException;

    CinemaDto save(CreateCinemaDto cinemaDto, String authorizedUsername);

    void update(long id, CinemaDto cinemaDto, String authorizedUsername);

    void deleteByID(long id, String authorizedUsername);
}
