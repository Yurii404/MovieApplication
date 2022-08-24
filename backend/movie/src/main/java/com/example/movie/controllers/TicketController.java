package com.example.movie.controllers;

import com.example.movie.dtos.CreateTicketDto;
import com.example.movie.dtos.TicketDto;
import com.example.movie.dtos.UserDto;
import com.example.movie.dtos.ValidationErrorDto;
import com.example.movie.services.TicketService;
import com.example.movie.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    private UserService userService;
    private TicketService ticketService;

    @Operation(summary = "Find all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))})
    })
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Find ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found ticket by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping({"/{id}"})
    public ResponseEntity<TicketDto> get(@PathVariable("id") int id) {
        return new ResponseEntity<>(ticketService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save new ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ticket dto or" +
                    " does not exist free tickets or movie session was already end",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    public ResponseEntity<TicketDto> save(@Valid @RequestBody CreateTicketDto createTicketDTO, Principal principal) {
        return new ResponseEntity<>(ticketService.save(createTicketDTO, principal.getName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Update ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ticket dto or" +
                    " does not exist free tickets or movie session was already end",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping({"/{id}"})
    public ResponseEntity<TicketDto> update(@PathVariable("id") int id, @Valid @RequestBody TicketDto ticketDTO,
                                            Principal principal) {
        ticketService.update(id, ticketDTO, principal.getName());
        return new ResponseEntity<>(ticketService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> delete(@PathVariable("id") int id, Principal principal) {
        ticketService.deleteByID(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Find the most active user by movie room id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the most active user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDto.class))})
    })
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping({"/analytics/user/movie-room/{id}"})
    public ResponseEntity<UserDto> getTheMostActiveUserByRoomID(@PathVariable long id, Principal principal) {
        return new ResponseEntity<>(userService.findById(ticketService.findTheMostActiveUserByRoomId(id, principal.getName())), HttpStatus.OK);
    }
}
