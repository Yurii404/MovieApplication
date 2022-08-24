package com.example.movie.dtos;

import com.example.movie.entities.ERole;
import com.example.movie.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    @JsonProperty
    private long id;

    @JsonProperty
    @NotBlank(message = "Username can't be empty")
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    @Email(message = "Invalid email")
    private String email;

    @JsonProperty
    @NotBlank(message = "First name can't be empty")
    private String firstName;

    @JsonProperty
    @NotBlank(message = "Second name can't be empty")
    private String secondName;

    @JsonProperty
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "Invalid phone number")
    private String phone;

    @JsonProperty
    private long roleId;

}