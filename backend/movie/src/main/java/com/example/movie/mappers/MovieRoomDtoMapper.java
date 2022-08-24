package com.example.movie.mappers;

import com.example.movie.dtos.CreateMovieRoomDto;
import com.example.movie.dtos.MovieRoomDto;
import com.example.movie.entities.Cinema;
import com.example.movie.entities.MovieRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieRoomDtoMapper {

    @Mapping(source = "cinema.id", target = "cinemaId")
    MovieRoomDto toDto(MovieRoom room);

    @Mapping(source = "cinema", target = "cinema")
    @Mapping(source = "roomDto.id", target = "id")
    MovieRoom toEntity(MovieRoomDto roomDto, Cinema cinema);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cinema", target = "cinema")
    MovieRoom toEntity(CreateMovieRoomDto roomDTO, Cinema cinema);
}
