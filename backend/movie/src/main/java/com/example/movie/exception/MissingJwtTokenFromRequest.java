package com.example.movie.exception;

public class MissingJwtTokenFromRequest extends RuntimeException {
    public MissingJwtTokenFromRequest() {
    }

    public MissingJwtTokenFromRequest(String message) {
        super(message);
    }

    public MissingJwtTokenFromRequest(String message, Throwable cause) {
        super(message, cause);
    }
}
