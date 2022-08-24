package com.example.movie.services;

import com.example.movie.dtos.CinemaDto;
import com.example.movie.dtos.CreateCinemaDto;
import com.example.movie.entities.Cinema;
import com.example.movie.entities.User;
import com.example.movie.exception.InvalidAccessRightsException;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.mappers.CinemaDtoMapper;
import com.example.movie.repositories.CinemaRepository;
import com.example.movie.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private CinemaDtoMapper cinemaDtoMapper;
    private CinemaRepository cinemaRepository;
    private UserRepository userRepository;

    @Override
    public List<CinemaDto> findAll() {
        List<Cinema> tickets = (List<Cinema>) cinemaRepository.findAll();
        return tickets.stream().map(cinemaDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CinemaDto findById(long id) throws ResourceNotFoundException {
        return cinemaDtoMapper
                .toDto(cinemaRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Cinema with id: " + id + " not found."))
                );
    }

    @Override
    @Transactional
    public CinemaDto save(CreateCinemaDto cinemaDto, String authorizedUsername) {
        User authorizedUser = userRepository.findUserByUsername(authorizedUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + authorizedUsername + " not found!"));

        if (cinemaDto.getUserId() == authorizedUser.getId()) {
            User user = userRepository.findById(cinemaDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinemaDto.getUserId() + " not found!"));

            Cinema cinemaToSave = cinemaDtoMapper.toEntity(cinemaDto, user);

            return cinemaDtoMapper.toDto(cinemaRepository.save(cinemaToSave));
        } else {
            throw new InvalidAccessRightsException("You can't create cinema for another user!");
        }
    }

    @Override
    @Transactional
    public void update(long id, CinemaDto cinemaDto, String authorizedUsername) {
        Cinema cinemaFromRep = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id: " + id + " not found."));

        if (cinemaFromRep.getUser().getUsername().equals(authorizedUsername)) {
            User userFromRep = userRepository.findById(cinemaDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with id:" + cinemaDto.getUserId() + " not found!"));

            cinemaFromRep.setName(cinemaDto.getName());
            cinemaFromRep.setUser(userFromRep);

            cinemaRepository.save(cinemaFromRep);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }

    }

    @Override
    public void deleteByID(long id, String authorizedUsername) {
        Cinema cinemaFromRep = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema with id: " + id + " not found."));

        if (cinemaFromRep.getUser().getUsername().equals(authorizedUsername)) {
            cinemaRepository.deleteById(id);
        } else {
            throw new InvalidAccessRightsException("You can't do this because you aren't this cinema admin");
        }
    }
}
