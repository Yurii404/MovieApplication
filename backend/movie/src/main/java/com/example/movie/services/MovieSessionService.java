package com.example.movie.services;

import com.example.movie.dtos.CreateMovieSessionDto;
import com.example.movie.dtos.MovieSessionDto;
import com.example.movie.exception.ResourceNotFoundException;

import java.util.List;

public interface MovieSessionService {
    List<MovieSessionDto> findAll();

    MovieSessionDto findById(long id) throws ResourceNotFoundException;

    MovieSessionDto save(CreateMovieSessionDto session, String authorizedUsername);

    void update(long id, MovieSessionDto session, String authorizedUsername);

    void deleteByID(long id, String authorizedUsername);
}
