package com.paulo.hotel.exception;

import net.bytebuddy.implementation.bind.annotation.Super;

public class QuartoReservadoException extends RuntimeException{

	
	public QuartoReservadoException(Long id) {
		// TODO Auto-generated constructor stub
		super(String.format("O quarto %d ja esta reservado nesta data",id));
	}
}
