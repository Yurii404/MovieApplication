package com.example.movie.exception;

public class MovieSessionWasEndException extends RuntimeException{
    public MovieSessionWasEndException(String message) {
        super(message);
    }
}
