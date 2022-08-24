package com.example.movie.services;

import com.example.movie.dtos.CreateMovieSessionDto;
import com.example.movie.dtos.MovieSessionDto;
import com.example.movie.entities.Cinema;
import com.example.movie.entities.MovieRoom;
import com.example.movie.entities.MovieSession;
import com.example.movie.entities.User;
import com.example.movie.exception.InvalidAccessRightsException;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.mappers.MovieSessionDtoMapper;
import com.example.movie.repositories.CinemaRepository;
import com.example.movie.repositories.MovieRoomRepository;
import com.example.movie.repositories.MovieSessionRepository;
import com.example.movie.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private MovieSessionRepository movieSessionRepository;
    private MovieSessionDtoMapper movieSessionDTOMapper;
    private MovieRoomRepository movieRoomRepository;
    private CinemaRepository cinemaRepository;
    private UserRepository userRepository;

    @Override
    public List<MovieSessionDto> findAll() {
        List<MovieSession> movieSession = (List<MovieSession>) movieSessionRepository.findAll();
        return movieSession.stream().map(movieSessionDTOMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MovieSessionDto findById(long id) throws ResourceNotFoundException {
        return movieSessionDTOMapper.toDTO(movieSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie session with id: " + id + " not found.")));
    }

    @Override
    public MovieSessionDto save(CreateMovieSessionDto session, String authorizedUsername) {
        MovieRoom movieRoom = movieRoomRepository.findById(session.getMovieRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + session.getMovieRoomId() + " not found."));

        Cinema cinema = cinemaRepository.findById(movieRoom.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + movieRoom.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            MovieSession movieSessionToSave = movieSessionDTOMapper.toEntity(session, movieRoom);
            movieSessionToSave.setNumOfTickets(movieRoom.getNumOfSeats());

            return movieSessionDTOMapper.toDTO(movieSessionRepository.save(movieSessionToSave));
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }
    }

    @Override
    @Transactional
    public void update(long id, MovieSessionDto session, String authorizedUsername) {
        MovieRoom movieRoom = movieRoomRepository.findById(session.getMovieRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + session.getMovieRoomId() + " not found."));

        Cinema cinema = cinemaRepository.findById(movieRoom.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + movieRoom.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            MovieSession movieSessionFromRep = movieSessionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie session with id: " + id + " not found."));

            movieSessionFromRep.setMovieRoom(movieRoom);
            movieSessionFromRep.setNameOfMovie(session.getNameOfMovie());
            movieSessionFromRep.setPrice(session.getPrice());
            movieSessionFromRep.setDateTime(session.getDateTime());
            movieSessionFromRep.setNumOfTickets(session.getNumOfTickets());

            movieSessionRepository.save(movieSessionFromRep);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }

    }

    @Override
    public void deleteByID(long id, String authorizedUsername) {
        MovieSession session = movieSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + id + " not found."));

        MovieRoom movieRoom = movieRoomRepository.findById(session.getMovieRoom().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + session.getMovieRoom().getId() + " not found."));

        Cinema cinema = cinemaRepository.findById(movieRoom.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + movieRoom.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            movieSessionRepository.deleteById(id);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }
    }
}
