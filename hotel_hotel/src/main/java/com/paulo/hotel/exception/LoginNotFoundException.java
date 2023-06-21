package com.paulo.hotel.exception;

public class LoginNotFoundException extends RuntimeException{

	public LoginNotFoundException() {
		super("Username or password invalid.");
		
		// TODO Auto-generated constructor stub
	}
	
}
