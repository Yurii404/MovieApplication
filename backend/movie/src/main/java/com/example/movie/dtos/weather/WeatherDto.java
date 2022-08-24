package com.example.movie.dtos.weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    private String name;

    private final LocalDateTime datetime = LocalDateTime.now();

    private WeatherMainDto main;

    private WeatherWindDto wind;
}
