package com.example.movie.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorDto {
    private final String devMessage;
    private final String userMessage;
    private final int code;
}