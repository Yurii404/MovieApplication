package com.example.movie.services;

import com.example.movie.dtos.CreateMovieRoomDto;
import com.example.movie.dtos.MovieRoomDto;
import com.example.movie.entities.Cinema;
import com.example.movie.entities.MovieRoom;
import com.example.movie.entities.User;
import com.example.movie.exception.InvalidAccessRightsException;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.mappers.MovieRoomDtoMapper;
import com.example.movie.repositories.CinemaRepository;
import com.example.movie.repositories.MovieRoomRepository;
import com.example.movie.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MovieRoomServiceImpl implements MovieRoomService {

    private MovieRoomRepository movieRoomRepository;
    private UserRepository userRepository;
    private MovieRoomDtoMapper movieRoomDTOMapper;
    private CinemaRepository cinemaRepository;

    @Override
    public List<MovieRoomDto> findAll() {
        List<MovieRoom> movieRooms = (List<MovieRoom>) movieRoomRepository.findAll();
        return movieRooms.stream().map(movieRoomDTOMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MovieRoomDto findById(long id) throws ResourceNotFoundException {
        return movieRoomDTOMapper.toDto(movieRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + id + " not found.")));
    }

    @Override
    public MovieRoomDto save(CreateMovieRoomDto room, String authorizedUsername) {
        Cinema cinema = cinemaRepository.findById(room.getCinemaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + room.getCinemaId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            MovieRoom movieRoomToSave = movieRoomDTOMapper.toEntity(room, cinema);

            return movieRoomDTOMapper.toDto(movieRoomRepository.save(movieRoomToSave));
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }
    }

    @Override
    @Transactional
    public void update(long id, MovieRoomDto room, String authorizedUsername) {
        Cinema cinema = cinemaRepository.findById(room.getCinemaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + room.getCinemaId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            MovieRoom movieRoomFromRep = movieRoomRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie room with id: " + id + " not found."));

            movieRoomFromRep.setRoomName(room.getRoomName());
            movieRoomFromRep.setNumOfSeats(room.getNumOfSeats());
            movieRoomFromRep.setRoomNumber(room.getRoomNumber());

            movieRoomRepository.save(movieRoomFromRep);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }
    }

    @Override
    public void deleteByID(long id, String authorizedUsername) {
        MovieRoom room = movieRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie room with id:" + id + " not found!"));

        Cinema cinema = cinemaRepository.findById(room.getCinema().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id:" + room.getCinema().getId() + " not found!"));

        User cinemaAdmin = userRepository.findById(cinema.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinema.getUser().getId() + " not found!"));

        if (cinemaAdmin.getUsername().equals(authorizedUsername)) {
            movieRoomRepository.deleteById(id);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }

    }
}
