package com.example.movie.dtos.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherMainDto {

    private double temp;

    private long pressure;

    private long humidity;
}
