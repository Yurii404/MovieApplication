package com.example.movie.controllers;


import com.example.movie.dtos.CreateMovieSessionDto;
import com.example.movie.dtos.MovieSessionDto;
import com.example.movie.dtos.ValidationErrorDto;
import com.example.movie.services.MovieSessionService;
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
@RequestMapping("/movie-session")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class MovieSessionController {
    private MovieSessionService movieSessionService;


    @Operation(summary = "Find all movie sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all movie session",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<MovieSessionDto>> getAll() {
        List<MovieSessionDto> movieSessions = movieSessionService.findAll();
        return new ResponseEntity<>(movieSessions, HttpStatus.OK);
    }

    @Operation(summary = "Find movie session by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie session by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie session not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @GetMapping({"/{id}"})
    public ResponseEntity<MovieSessionDto> get(@PathVariable("id") int id) {
        return new ResponseEntity<>(movieSessionService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save new movie session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie session was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid movie session dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<MovieSessionDto> save(@Valid @RequestBody CreateMovieSessionDto createMovieSessionDTO,
                                                Principal principal) {
        return new ResponseEntity<>(movieSessionService.save(createMovieSessionDTO, principal.getName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Update movie session by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie session was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid movie session dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Movie session not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping({"/{id}"})
    public ResponseEntity<MovieSessionDto> update(@PathVariable("id") int id,
                                                  @Valid @RequestBody MovieSessionDto movieSessionDTO, Principal principal) {
        movieSessionService.update(id, movieSessionDTO, principal.getName());
        return new ResponseEntity<>(movieSessionService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete movie session by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie session was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie session not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> delete(@PathVariable("id") int id, Principal principal) {
        movieSessionService.deleteByID(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
