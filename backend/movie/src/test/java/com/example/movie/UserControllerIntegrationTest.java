package com.example.movie;

import com.example.movie.dtos.CreateUserDto;
import com.example.movie.dtos.UserDto;
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
public class UserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnCreatedUser() throws Exception {
        CreateUserDto testUser = new CreateUserDto("Yurii",
                "yurii1@gmail.com", "Yurii", "Yurii", "380932323223", "11111", 1);
        ResponseEntity<UserDto> response = restTemplate
                .postForEntity("/users", testUser, UserDto.class);
        UserDto user = response.getBody();
        assertNotNull(user);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(user.getUsername(), testUser.getUsername());
    }

    @Test
    public void shouldReturnUpdatedUser() throws Exception {
        UserDto updatedUser = new UserDto(3, "YuriiUp",
                "yurii@gmail.com", "YuriiUp", "YuriiUp", "380932323223", "111", 1);

        HttpEntity<UserDto> request = new HttpEntity<>(updatedUser);
        ResponseEntity<UserDto> response = restTemplate
                .exchange("/users/3", HttpMethod.PUT, request, UserDto.class);
        UserDto user = response.getBody();
        assertNotNull(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, updatedUser);
    }

    @Test
    public void shouldReturnDeletedUser() throws Exception {
        ResponseEntity<UserDto> response = restTemplate
                .exchange("/users/2", HttpMethod.DELETE, new HttpEntity<>(""), UserDto.class);
        UserDto user = response.getBody();
        assertNull(user);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        ResponseEntity<UserDto> response = restTemplate
                .getForEntity("/users/3", UserDto.class);
        UserDto user = response.getBody();
        assertNotNull(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        ResponseEntity<UserDto[]> response = restTemplate
                .getForEntity("/users", UserDto[].class);
        UserDto[] users = response.getBody();
        assertNotNull(users);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, users.length);
    }

    @Test
    public void shouldReturnValidationErrorDTO_userNotFound() throws Exception {
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .getForEntity("/users/100", ValidationErrorDto.class);
        ValidationErrorDto errorDTO = response.getBody();
        assertNotNull(errorDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnValidationErrorDTO_byEmail() throws Exception {
        CreateUserDto testUser = new CreateUserDto("Yurii",
                "invalidEmail", "Yurii", "Yurii", "380932323223", "111", 1);
        ValidationErrorDto validationErrorDTO = new ValidationErrorDto(null, "Invalid email", 400);
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .postForEntity("/users", testUser, ValidationErrorDto.class);
        ValidationErrorDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseBody.getUserMessage(), validationErrorDTO.getUserMessage());
    }

    @Test
    public void shouldReturnValidationErrorDTO_byPhone() throws Exception {
        CreateUserDto testUser = new CreateUserDto("Yurii",
                "yurii@gmail.com", "Yurii", "Yurii", "invalidPhone", "111", 1);
        ValidationErrorDto validationErrorDTO = new ValidationErrorDto(null, "Invalid phone number", 400);
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .postForEntity("/users", testUser, ValidationErrorDto.class);
        ValidationErrorDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseBody.getUserMessage(), validationErrorDTO.getUserMessage());
    }

    @Test
    public void shouldReturnValidationErrorDTO_byUserName() throws Exception {
        CreateUserDto testUser = new CreateUserDto(null,
                "yurii@gmail.com", "Yurii", "Yurii", "380842749418", "1111", 1);
        ValidationErrorDto validationErrorDTO = new ValidationErrorDto(null, "Username can't be empty", 400);
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .postForEntity("/users", testUser, ValidationErrorDto.class);
        ValidationErrorDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseBody.getUserMessage(), validationErrorDTO.getUserMessage());
    }

    @Test
    public void shouldReturnValidationErrorDTO_byFirstName() throws Exception {
        CreateUserDto testUser = new CreateUserDto("Yurii",
                "yurii@gmail.com", null, "Yurii", "380842749418", "111", 1);
        ValidationErrorDto validationErrorDTO = new ValidationErrorDto(null, "First name can't be empty", 400);
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .postForEntity("/users", testUser, ValidationErrorDto.class);
        ValidationErrorDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseBody.getUserMessage(), validationErrorDTO.getUserMessage());
    }

    @Test
    public void shouldReturnValidationErrorDTO_bySecondName() throws Exception {
        CreateUserDto testUser = new CreateUserDto("Yurii",
                "yurii@gmail.com", "Yurii", null, "380842749418", "1111", 1);
        ValidationErrorDto validationErrorDTO = new ValidationErrorDto(null, "Second name can't be empty", 400);
        ResponseEntity<ValidationErrorDto> response = restTemplate
                .postForEntity("/users", testUser, ValidationErrorDto.class);
        ValidationErrorDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseBody.getUserMessage(), validationErrorDTO.getUserMessage());
    }
}