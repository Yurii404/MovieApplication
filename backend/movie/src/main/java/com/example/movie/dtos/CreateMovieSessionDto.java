package com.example.movie.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CreateMovieSessionDto {
    @JsonProperty
    private String nameOfMovie;

    @JsonProperty
    private long movieRoomId;

    @JsonProperty
    private int price;

    @JsonProperty
    private LocalDateTime dateTime;

}
