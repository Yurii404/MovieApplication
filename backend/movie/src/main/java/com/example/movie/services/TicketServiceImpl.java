package com.example.movie.services;

import com.example.movie.dtos.CreateTicketDto;
import com.example.movie.dtos.TicketDto;
import com.example.movie.entities.Cinema;
import com.example.movie.entities.MovieRoom;
import com.example.movie.entities.MovieSession;
import com.example.movie.entities.Ticket;
import com.example.movie.entities.User;
import com.example.movie.exception.InvalidAccessRightsException;
import com.example.movie.exception.MovieSessionWasEndException;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.exception.TicketsDoesNotLeftException;
import com.example.movie.mappers.TicketDtoMapper;
import com.example.movie.repositories.CinemaRepository;
import com.example.movie.repositories.MovieRoomRepository;
import com.example.movie.repositories.MovieSessionRepository;
import com.example.movie.repositories.TicketRepository;
import com.example.movie.repositories.UserRepository;
import com.example.movie.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private MovieSessionRepository movieSessionRepository;
    private UserRepository userRepository;
    private TicketDtoMapper ticketDTOMapper;
    private CinemaRepository cinemaRepository;
    private MovieRoomRepository movieRoomRepository;



    @Override
    public List<TicketDto> findAll() {
        List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
        return tickets.stream().map(ticketDTOMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TicketDto findById(long id) throws ResourceNotFoundException {
        return ticketDTOMapper
                .toDto(ticketRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Ticket with id: " + id + " not found."))
                );
    }

    @Override
    @Transactional
    public TicketDto save(CreateTicketDto createTicketDTO, String authorizedUsername) {


        User user = userRepository.findUserByUsername(authorizedUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + authorizedUsername + " not found!"));

        MovieSession movieSession = movieSessionRepository.findById(createTicketDTO.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie session with id:" + createTicketDTO.getSessionId() + " not found!"));


        if (movieSession.getDateTime().isAfter(LocalDateTime.now())) {
            if (movieSession.getNumOfTickets() != 0) {
                movieSession.setNumOfTickets(movieSession.getNumOfTickets() - 1);

                movieSessionRepository.save(movieSession);

                Ticket ticketToSave = ticketDTOMapper.toEntity(user, movieSession);

                return ticketDTOMapper.toDto(ticketRepository.save(ticketToSave));

            } else {
                throw new TicketsDoesNotLeftException("There are no free tickets for movie session with id: "
                        + createTicketDTO.getSessionId() + "!");
            }
        } else {
            throw new MovieSessionWasEndException("Movie session with id:"
                    + createTicketDTO.getSessionId() + "was already end!");
        }

    }

    @Override
    @Transactional
    public void update(long id, TicketDto ticket, String authorizedUsername) {
        Ticket ticketFromRep = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id: " + id + " not found."));

        User userTicketOwner = userRepository.findById(ticketFromRep.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + ticketFromRep.getUser().getId() + " not found!"));

        MovieSession movieSession = movieSessionRepository.findById(ticket.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie session with id:" + ticket.getSessionId() + " not found!"));

        MovieRoom movieRoom = movieRoomRepository.findById(movieSession.getMovieRoom().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + movieSession.getMovieRoom().getId() + " not found."));

        Cinema cinema = cinemaRepository.findById(movieRoom.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + movieRoom.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (userTicketOwner.getUsername().equals(authorizedUsername)
                && cinemaAdmin.getUsername().equals(authorizedUsername)) {

            ticketFromRep.setUser(userTicketOwner);
            ticketFromRep.setMovieSession(movieSession);

            ticketRepository.save(ticketFromRep);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin or ticket owner");
        }

    }

    @Transactional
    @Override
    public void deleteByID(long id, String authorizedUsername) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id: " + id + " not found."));

        User userTicketOwner = userRepository.findById(ticket.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + ticket.getUser().getId() + " not found!"));

        MovieSession movieSession = movieSessionRepository.findById(ticket.getMovieSession().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie session with id:" + ticket.getMovieSession().getId() + " not found!"));

        MovieRoom movieRoom = movieRoomRepository.findById(movieSession.getMovieRoom().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + movieSession.getMovieRoom().getId() + " not found."));

        Cinema cinema = cinemaRepository.findById(movieRoom.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + movieRoom.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (userTicketOwner.getUsername().equals(authorizedUsername)
                && cinemaAdmin.getUsername().equals(authorizedUsername)) {
            movieSession.setNumOfTickets(movieSession.getNumOfTickets() + 1);

            movieSessionRepository.save(movieSession);

            ticketRepository.deleteById(id);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin or ticket owner");
        }
    }

    @Override
    public long findTheMostActiveUserByRoomId(long movieRoomId, String authorizedUsername) {
        MovieRoom movieRoom = movieRoomRepository.findById(movieRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + movieRoomId + " not found."));

        Cinema cinema = cinemaRepository.findById(movieRoom.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + movieRoom.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            return ticketRepository.findTheMostActiveUserByRoomId(movieRoomId);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin or ticket owner");
        }
    }
}
