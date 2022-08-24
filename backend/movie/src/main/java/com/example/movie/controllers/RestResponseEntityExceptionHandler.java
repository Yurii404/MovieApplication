package com.example.movie.controllers;

import com.example.movie.dtos.ValidationErrorDto;
import com.example.movie.exception.InvalidAccessRightsException;
import com.example.movie.exception.MovieSessionWasEndException;
import com.example.movie.exception.ResourceNotFoundException;
import com.example.movie.exception.TicketsDoesNotLeftException;
import org.hibernate.JDBCException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String userMessage = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        ValidationErrorDto validationErrorDTO = new ValidationErrorDto(
                ex.getMessage(),
                userMessage,
                status.value()
        );
        return handleExceptionInternal(ex, validationErrorDTO, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ValidationErrorDto> handleResourceNotFound(ResourceNotFoundException e) {
        String devMessage = e.getMessage();
        String userMessage = String.format("Resource not found!");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketsDoesNotLeftException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ValidationErrorDto> handleTicketsDoesNotLeft(TicketsDoesNotLeftException e) {
        String devMessage = e.getMessage();
        String userMessage = String.format("There are no free tickets for this session!");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieSessionWasEndException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ValidationErrorDto> handleTicketsDoesNotLeft(MovieSessionWasEndException e) {
        String devMessage = e.getMessage();
        String userMessage = String.format("This session was already end!");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccessRightsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorDto> handleInvalidAccessRights(InvalidAccessRightsException e) {
        String devMessage = e.getMessage();
        String userMessage = String.format("You can't do this because you haven't rights");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorDto> handleAuthenticationException(AuthenticationException e) {
        String devMessage = e.getMessage();
        String userMessage = String.format("User with this credentials doesn't exist.");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorDto> handleInvalidAccessRights(AccessDeniedException e) {
        String devMessage = e.getMessage();
        String userMessage = String.format("You haven't access to do this.");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JDBCException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorDto> handleJDBCException(JDBCException e) {
        String devMessage = e.getSQLException().getMessage();
        String userMessage = String.format("Some trouble with db.");
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(devMessage, userMessage,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }

}