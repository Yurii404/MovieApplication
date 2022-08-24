package com.example.movie.controllers;

import com.example.movie.dtos.CreateMovieRoomDto;
import com.example.movie.dtos.MovieRoomDto;
import com.example.movie.dtos.ValidationErrorDto;
import com.example.movie.services.MovieRoomService;
import com.example.movie.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/movie-rooms")
@AllArgsConstructor

public class MovieRoomController {
    private MovieRoomService movieRoomService;
    private UserService userService;

    @Operation(summary = "Find all movie rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all movie rooms",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieRoomDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<MovieRoomDto>> getAll() {
        List<MovieRoomDto> movieRoomDtos = movieRoomService.findAll();
        return new ResponseEntity<>(movieRoomDtos, HttpStatus.OK);
    }

    @Operation(summary = "Find movie room by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie room by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieRoomDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie room not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping({"/{id}"})
    public ResponseEntity<MovieRoomDto> get(@PathVariable("id") int id) {
        return new ResponseEntity<>(movieRoomService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save new movie room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie room was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieRoomDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid movie room dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<MovieRoomDto> save(@Valid @RequestBody CreateMovieRoomDto movieRoomDTO, Principal principal) {
        return new ResponseEntity<>(movieRoomService.save(movieRoomDTO, principal.getName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Update movie room by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie room was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieRoomDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid movie room dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))}),
            @ApiResponse(responseCode = "404", description = "Movie room not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping({"/{id}"})
    public ResponseEntity<MovieRoomDto> update(@PathVariable("id") int id, @Valid @RequestBody MovieRoomDto movieRoomDTO,
                                               Principal principal) {
        movieRoomService.update(id, movieRoomDTO, principal.getName());
        return new ResponseEntity<>(movieRoomService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete movie room by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie room was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie room not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> delete(@PathVariable("id") int id, Principal principal) {
        movieRoomService.deleteByID(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
