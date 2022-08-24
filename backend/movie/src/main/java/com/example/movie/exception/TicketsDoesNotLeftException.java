package com.example.movie.exception;

public class TicketsDoesNotLeftException extends RuntimeException{
    public TicketsDoesNotLeftException(String message) {
        super(message);
    }
}
