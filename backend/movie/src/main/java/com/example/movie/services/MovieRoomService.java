package com.example.movie.services;

import com.example.movie.dtos.CreateMovieRoomDto;
import com.example.movie.dtos.MovieRoomDto;
import com.example.movie.exception.ResourceNotFoundException;

import java.util.List;

public interface MovieRoomService {
    List<MovieRoomDto> findAll();

    MovieRoomDto findById(long id) throws ResourceNotFoundException;

    MovieRoomDto save(CreateMovieRoomDto room, String authorizedUsername);

    void update(long id, MovieRoomDto room, String authorizedUsername);

    void deleteByID(long id, String authorizedUsername);
}
