package com.example.movie.mappers;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.entities.Role;
import com.example.movie.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(source = "role.id", target = "roleId")
    UserDto toDto(User user);

    @Mapping(source = "userDTO.id", target = "id")
    @Mapping(source = "role", target = "role")
    User toEntity(UserDto userDTO, Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "role", target = "role")
    User toEntity(CreateUserDto userDTO, Role role);

}