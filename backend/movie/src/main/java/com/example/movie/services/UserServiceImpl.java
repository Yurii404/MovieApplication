package com.example.movie.services;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.entities.Role;
import com.example.movie.entities.User;
import com.example.movie.exception.InvalidAccessRightsException;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.mappers.UserDtoMapper;
import com.example.movie.repositories.RoleRepository;
import com.example.movie.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserDtoMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {

        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(userDTOMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(long id) throws ResourceNotFoundException {
        Optional<User> movieRoom = userRepository.findById(id);

        if (movieRoom.isPresent()) {
            return userDTOMapper.toDto(movieRoom.get());
        } else {
            throw new ResourceNotFoundException("User with id: " + id + " not found.");
        }
    }

    @Transactional
    public UserDto findByName(String name) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(name);

        if (user.isPresent()) {
            return userDTOMapper.toDto(user.get());
        } else {
            throw new ResourceNotFoundException("User with name: " + user + " not found.");
        }
    }

    @Override
    public UserDto save(CreateUserDto user) {

        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Role with this id not found: " + user.getRoleId());
                });

        User userToSave = userDTOMapper.toEntity(user, userRole);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));

        return userDTOMapper.toDto(userRepository.save(userToSave));
    }

    @Override
    @Transactional
    public void update(long id, UserDto user, String authorizedUsername) {
        User userFromRep = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found."));

        if (userFromRep.getUsername().equals(authorizedUsername)) {
            userFromRep.setFirstName(user.getFirstName());
            userFromRep.setSecondName(user.getSecondName());
            userFromRep.setUsername(user.getUsername());
            userFromRep.setEmail(user.getEmail());
            userFromRep.setPhone(user.getPhone());
            userRepository.save(userFromRep);
        } else {
            throw new InvalidAccessRightsException("You can't can't update another users");
        }
    }

    @Override
    public void deleteByID(long userId, String authorizedUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found."));

        if (user.getUsername().equals(authorizedUsername)) {
            userRepository.deleteById(userId);
        } else {
            throw new InvalidAccessRightsException("You can't can't delete another users");
        }

    }
}