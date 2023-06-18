package com.paulo.hotel.exception;

public class QuartoNotFoundException extends RuntimeException {

	
	public QuartoNotFoundException(Long id) {
		
		super(String.format("Nao foi encontrado o quarto %d",id));

	}
}
