package com.paulo.hotel.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SessionDTO {

	private String token;
	
	private String usuario;
	
	private Date dataExpiracao;
}
