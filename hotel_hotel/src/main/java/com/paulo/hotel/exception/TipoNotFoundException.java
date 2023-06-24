package com.paulo.hotel.exception;


public class TipoNotFoundException extends RuntimeException{
	
	public TipoNotFoundException(Long tipo) {
		// TODO Auto-generated constructor stub
	
	super(String.format("Tipo %d not found ",tipo));
	
	}
	
	public TipoNotFoundException(String tipo) {
		// TODO Auto-generated constructor stub
	
	super(String.format("Tipo %d not found ",tipo));
	
	}
}
