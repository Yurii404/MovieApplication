package com.example.movie.controllers;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.dtos.ValidationErrorDto;
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
@RequestMapping("/users")
@AllArgsConstructor

public class UserController {
    private final UserService userService;

    @Operation(summary = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))})
    })
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }


    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping({"/{id}"})
    public ResponseEntity<UserDto> get(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody CreateUserDto userDTO) {
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user dto ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping({"/{id}"})
    public ResponseEntity<UserDto> update(@PathVariable("id") int id, @Valid @RequestBody UserDto userDTO, Principal principal) {
        userService.update(id, userDTO, principal.getName());
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> delete(@PathVariable("id") int id, Principal principal) {
        userService.deleteByID(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
