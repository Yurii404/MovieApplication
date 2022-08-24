package com.example.movie.dtos;

import com.example.movie.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CinemaDto {
    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private long userId;

}
