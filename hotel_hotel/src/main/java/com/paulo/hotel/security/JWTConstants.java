package com.paulo.hotel.security;

public class JWTConstants {

	public static final int PERIOD=86400000;
	public static final String PREFIX="Bearer ";
	public static final String HEADER_TOKEN="Authorization";
	public static final String SECRET_KEY="PALAVRA_SECRETA";
	public static final String HEADER_ROLES="authorities";
}
