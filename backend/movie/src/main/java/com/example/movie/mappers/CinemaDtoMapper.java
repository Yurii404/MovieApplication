package com.example.movie.mappers;

import com.example.movie.dtos.CinemaDto;
import com.example.movie.dtos.CreateCinemaDto;
import com.example.movie.dtos.CreateMovieRoomDto;
import com.example.movie.dtos.MovieRoomDto;
import com.example.movie.entities.Cinema;
import com.example.movie.entities.MovieRoom;
import com.example.movie.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CinemaDtoMapper {

    @Mapping(source = "user.id", target = "userId")
    CinemaDto toDto(Cinema cinema);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "cinemaDto.id", target = "id")
    Cinema toEntity(CinemaDto cinemaDto, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "user")
    Cinema toEntity(CreateCinemaDto cinemaDto, User user);
}
