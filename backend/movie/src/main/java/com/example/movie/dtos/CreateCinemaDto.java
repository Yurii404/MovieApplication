package com.example.movie.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateCinemaDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private long userId;
}
