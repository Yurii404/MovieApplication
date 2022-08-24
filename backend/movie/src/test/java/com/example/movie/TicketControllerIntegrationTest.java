package com.example.movie;

import com.example.movie.dtos.CreateTicketDto;
import com.example.movie.dtos.TicketDto;
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

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnCreatedTicket() throws Exception {
        CreateTicketDto testTicket = new CreateTicketDto(2, 2);
        ResponseEntity<TicketDto> response = restTemplate.postForEntity("/tickets", testTicket, TicketDto.class);

        TicketDto ticket = response.getBody();
        assertNotNull(ticket);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturnUpdatedTicket() throws Exception {
        TicketDto updatedTicket = new TicketDto(3, 2, 2);

        HttpEntity<TicketDto> request = new HttpEntity<>(updatedTicket);
        ResponseEntity<TicketDto> response = restTemplate
                .exchange("/tickets/3", HttpMethod.PUT, request, TicketDto.class);
        TicketDto ticketDTO = response.getBody();
        assertNotNull(ticketDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void shouldReturnDeletedTicket() throws Exception {
        ResponseEntity<TicketDto> response = restTemplate
                .exchange("/tickets/4", HttpMethod.DELETE, new HttpEntity<>(""), TicketDto.class);
        TicketDto ticketDTO = response.getBody();
        assertNull(ticketDTO);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturnTicketById() throws Exception {
        ResponseEntity<TicketDto> response = restTemplate
                .getForEntity("/tickets/2", TicketDto.class);
        TicketDto ticketDTO = response.getBody();
        assertNotNull(ticketDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnAllTickets() throws Exception {
        ResponseEntity<TicketDto[]> response = restTemplate
                .getForEntity("/tickets", TicketDto[].class);
        TicketDto[] ticketDtos = response.getBody();
        assertNotNull(ticketDtos);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, ticketDtos.length);
    }

    @Test
    public void shouldReturnValidationErrorDTO_ticketNotFound() throws Exception {
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .getForEntity("/tickets/100", ValidationErrorDto.class);
        ValidationErrorDto errorDTO = response.getBody();
        assertNotNull(errorDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}