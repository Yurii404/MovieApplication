package com.example.movie.services;

import com.example.movie.dtos.CreateTicketDto;
import com.example.movie.dtos.TicketDto;
import com.example.movie.exception.ResourceNotFoundException;

import java.util.List;

public interface TicketService {
    List<TicketDto> findAll();

    TicketDto findById(long id) throws ResourceNotFoundException;

    TicketDto save(CreateTicketDto ticket, String authorizedUsername);

    void update(long id, TicketDto ticket, String authorizedUsername);

    void deleteByID(long id, String authorizedUsername);

    long findTheMostActiveUserByRoomId(long movieRoomId, String authorizedUsername);
}
