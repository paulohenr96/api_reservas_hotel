package com.paulo.hotel.security;

import java.util.List;

import com.paulo.hotel.dto.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTCreator {

	
	@SuppressWarnings("deprecation")
	public static String createJWT(TokenDTO newTokenInformation) {
		
		
		JwtBuilder setExpiration = Jwts.builder().setExpiration(newTokenInformation.getDateEnd());
		JwtBuilder setSubject = setExpiration.setSubject(newTokenInformation.getSubject());
		
		JwtBuilder setIssuedAt = setSubject.setIssuedAt(newTokenInformation.getDateStart());
		
		JwtBuilder claim = setIssuedAt.claim(JWTConstants.HEADER_ROLES,newTokenInformation.getRoles());
		
		
		String compact = claim.signWith(SignatureAlgorithm.HS256,JWTConstants.SECRET_KEY).compact();
	
		String tokenTxt = JWTConstants.PREFIX.concat(compact);
		
		return tokenTxt;
		
		
	}
	
	@SuppressWarnings("deprecation")
	public static TokenDTO authToken(String fullToken) {
		String key = fullToken.replace(JWTConstants.PREFIX, "");
	
	
		 Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(JWTConstants.SECRET_KEY).parseClaimsJws(key);
		
		
		 String subject = parseClaimsJws.getBody().getSubject();
		 List<String> roles=(List<String>) parseClaimsJws.getBody().get(JWTConstants.HEADER_ROLES);

		 
		 TokenDTO tokenInfo=new TokenDTO();
		 
		 tokenInfo.setSubject(subject);
		 tokenInfo.setRoles(roles);
	
		 
		 
		 return tokenInfo;
	}
	
}
