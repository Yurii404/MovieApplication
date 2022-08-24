package com.example.movie.mappers;

import com.example.movie.dtos.CreateMovieSessionDto;
import com.example.movie.dtos.MovieSessionDto;
import com.example.movie.entities.MovieRoom;
import com.example.movie.entities.MovieSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieSessionDtoMapper {

    @Mapping(source = "movieRoom.id", target = "movieRoomId")
    MovieSessionDto toDTO(MovieSession session);

    @Mapping(source = "sessionDTO.id", target = "id")
    MovieSession toEntity(MovieSessionDto sessionDTO, MovieRoom movieRoom);

    @Mapping(target = "id", ignore = true)
    MovieSession toEntity(CreateMovieSessionDto sessionDTO, MovieRoom movieRoom);

}
