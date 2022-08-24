package com.example.movie.controllers;

import com.example.movie.aspects.Audit;
import com.example.movie.dtos.weather.WeatherDto;
import com.example.movie.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor

public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "Get current weather in Lviv")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather received",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WeatherDto.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather data not found",
                    content = @Content
            )
    })
    @GetMapping
    @Audit
    public WeatherDto getWeather() {
        return weatherService.getWeather();
    }
}