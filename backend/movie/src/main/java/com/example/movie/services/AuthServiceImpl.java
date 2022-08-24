package com.example.movie.services;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.TokenDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.dtos.UserLoginDto;
import com.example.movie.entities.ERole;
import com.example.movie.entities.Role;
import com.example.movie.entities.User;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.mappers.UserDtoMapper;
import com.example.movie.repositories.RoleRepository;
import com.example.movie.repositories.UserRepository;
import com.example.movie.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    public UserDto register(CreateUserDto user) {
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Role with this id not found: " + user.getRoleId());
                });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userToSave = userDtoMapper.toEntity(user, userRole);


        return userDtoMapper.toDto(userRepository.save(userToSave));
    }

    public TokenDto login(UserLoginDto loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenDto(tokenProvider.generateToken(authentication));
    }
}
