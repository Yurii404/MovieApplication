package com.example.movie.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class TokenDto {

	@NotBlank
	private String token;
}
