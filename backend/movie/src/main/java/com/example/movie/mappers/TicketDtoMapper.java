package com.example.movie.mappers;

import com.example.movie.dtos.TicketDto;
import com.example.movie.entities.MovieSession;
import com.example.movie.entities.Ticket;
import com.example.movie.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketDtoMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "movieSession.id", target = "sessionId")
    TicketDto toDto(Ticket ticket);

    @Mapping(source = "ticketDTO.id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "movieSession", target = "movieSession")
    Ticket toEntity(TicketDto ticketDTO, User user, MovieSession movieSession);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(source = "movieSession", target = "movieSession")
    Ticket toEntity(User user, MovieSession movieSession);
}
