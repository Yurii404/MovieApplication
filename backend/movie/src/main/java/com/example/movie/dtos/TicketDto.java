package com.example.movie.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TicketDto {
    @JsonProperty
    private long id;

    @JsonProperty
    private long userId;

    @JsonProperty
    private long sessionId;
}
