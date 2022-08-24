package com.example.movie.exception;

public class InvalidAccessRightsException extends RuntimeException {
    public InvalidAccessRightsException() {
    }

    public InvalidAccessRightsException(String message) {
        super(message);
    }

    public InvalidAccessRightsException(String message, Throwable cause) {
        super(message, cause);
    }
}
