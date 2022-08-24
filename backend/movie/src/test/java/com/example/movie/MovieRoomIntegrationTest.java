package com.example.movie;

import com.example.movie.dtos.CreateMovieRoomDto;
import com.example.movie.dtos.MovieRoomDto;
import com.example.movie.dtos.ValidationErrorDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
public class MovieRoomIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnCreatedMovieRoom() throws Exception {
        CreateMovieRoomDto testMovieRoom = new CreateMovieRoomDto("First room",
                1, 5, 1);
        ResponseEntity<MovieRoomDto> response = restTemplate.postForEntity("/movie-rooms", testMovieRoom, MovieRoomDto.class);

        MovieRoomDto movieRoom = response.getBody();
        assertNotNull(movieRoom);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movieRoom.getRoomName(), movieRoom.getRoomName());
    }

    @Test
    public void shouldReturnUpdatedMovieRoom() throws Exception {
        MovieRoomDto updatedMovieRoom = new MovieRoomDto(2, "Updated room",
                2, 5, 1);

        HttpEntity<MovieRoomDto> request = new HttpEntity<>(updatedMovieRoom);
        ResponseEntity<MovieRoomDto> response = restTemplate
                .exchange("/movie-rooms/2", HttpMethod.PUT, request, MovieRoomDto.class);
        MovieRoomDto movieRoomDTO = response.getBody();
        assertNotNull(movieRoomDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnDeletedMovieRoom() throws Exception {
        ResponseEntity<MovieRoomDto> response = restTemplate
                .exchange("/movie-rooms/3", HttpMethod.DELETE, new HttpEntity<>(""), MovieRoomDto.class);
        MovieRoomDto movieRoomDTO = response.getBody();
        assertNull(movieRoomDTO);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturnMovieRoomById() throws Exception {
        ResponseEntity<MovieRoomDto> response = restTemplate
                .getForEntity("/movie-rooms/4", MovieRoomDto.class);
        MovieRoomDto movieRoom = response.getBody();
        assertNotNull(movieRoom);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnAllMovieRooms() throws Exception {
        ResponseEntity<MovieRoomDto[]> response = restTemplate
                .getForEntity("/movie-rooms", MovieRoomDto[].class);
        MovieRoomDto[] movieRooms = response.getBody();
        assertNotNull(movieRooms);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, movieRooms.length);
    }

    @Test
    public void shouldReturnValidationErrorDTO_movieRoomNotFound() throws Exception {
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .getForEntity("/movie-rooms/100", ValidationErrorDto.class);
        ValidationErrorDto errorDTO = response.getBody();
        assertNotNull(errorDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}