package com.example.movie.controllers;

import com.example.movie.dtos.CinemaDto;
import com.example.movie.dtos.CreateCinemaDto;
import com.example.movie.dtos.ValidationErrorDto;
import com.example.movie.services.CinemaService;
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
@RequestMapping("/cinema")
@AllArgsConstructor

public class CinemaController {
    private final CinemaService cinemaService;

    @Operation(summary = "Find all cinemas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all cinemas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<CinemaDto>> getAll() {
        return new ResponseEntity<>(cinemaService.findAll(), HttpStatus.OK);
    }


    @Operation(summary = "Find cinema by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found cinema by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cinema not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping({"/{id}"})
    public ResponseEntity<CinemaDto> get(@PathVariable("id") int id) {
        return new ResponseEntity<>(cinemaService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save new cinema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cinema was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid cinema dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<CinemaDto> save(@Valid @RequestBody CreateCinemaDto cinemaDto, Principal principal) {
        return new ResponseEntity<>(cinemaService.save(cinemaDto, principal.getName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Update cinema by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cinema was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid cinema dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Cinema not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping({"/{id}"})
    public ResponseEntity<CinemaDto> update(@PathVariable("id") int id,
                                            @Valid @RequestBody CinemaDto cinemaDto, Principal principal) {
        cinemaService.update(id, cinemaDto, principal.getName());
        return new ResponseEntity<>(cinemaService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete cinema by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cinema was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cinema not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> delete(@PathVariable("id") int id, Principal principal) {
        cinemaService.deleteByID(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
