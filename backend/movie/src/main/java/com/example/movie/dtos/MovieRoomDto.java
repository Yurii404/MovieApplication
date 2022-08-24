package com.example.movie.dtos;

import com.example.movie.entities.Cinema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class MovieRoomDto {

    @JsonProperty
    private long id;

    @JsonProperty
    @NotBlank(message = "Room name can't be empty")
    private String roomName;

    @JsonProperty
    @NotNull(message = "Room number can't be empty")
    private int roomNumber;

    @JsonProperty
    @NotNull(message = "Num of seats can't be empty")
    private int numOfSeats;

    @JsonProperty
    @NotNull(message = "Movie room should be in some cinema")
    private long cinemaId;
}
