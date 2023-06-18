package com.paulo.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

	
	public UserNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		
		super(String.format("O id : %d nao foi encontrado.",id));
	
	}
}
