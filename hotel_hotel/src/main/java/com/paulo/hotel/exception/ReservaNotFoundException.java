package com.paulo.hotel.exception;



public class ReservaNotFoundException extends RuntimeException{

	public ReservaNotFoundException(Long id) {
	
		super(String.format("Nao foi encontrada reserva com o id %d",id));
		// TODO Auto-generated constructor stub
	}
	
}
