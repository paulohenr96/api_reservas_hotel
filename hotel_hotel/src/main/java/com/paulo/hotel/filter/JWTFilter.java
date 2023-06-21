package com.paulo.hotel.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paulo.hotel.dto.ErroDTO;
import com.paulo.hotel.dto.TokenDTO;
import com.paulo.hotel.security.JWTConstants;
import com.paulo.hotel.security.JWTCreator;

import io.jsonwebtoken.JwtException;

public class JWTFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		try {
			String token=request.getHeader(JWTConstants.HEADER_TOKEN);
			
			
			if (token==null || token.equals("null")) {
				SecurityContextHolder.clearContext();
			}else {
				
				TokenDTO authToken = JWTCreator.authToken(token);
				
				SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(
						authToken.getSubject(),
						null,
						authToken
							.getRoles()
								.stream()
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList())));
			}
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			// TODO Auto-generated catch block
			ErroDTO tratarErros = new ErroDTO(HttpStatus.NOT_ACCEPTABLE.value(),"Problema no Token => "+e.getMessage());
            StringBuilder sb=new StringBuilder();
            
            sb.append("{\"status\":\"");
			sb.append(tratarErros.getStatus());
			sb.append("\",");
            sb.append("\"erro\":\"");
        	sb.append(tratarErros.getErro());
			sb.append("\"}");
			response.getWriter().write(sb.toString());	
            response.getWriter().flush();		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
