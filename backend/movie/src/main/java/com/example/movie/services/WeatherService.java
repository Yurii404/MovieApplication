package com.example.movie.services;

import com.example.movie.dtos.weather.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WeatherService {

    private final WebClient webClient;
    @Value("${weather.url}")
    private String url;


    @Bean
    public WeatherDto getWeather() {
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherDto.class)
                .block();
    }

}