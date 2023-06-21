package com.paulo.hotel.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

	
	private String subject;
	private Date dateStart;
	private Date dateEnd;
	
	
	private String completeToken;
	
	private List<String> roles;
	
	
	
	
	
	
	
	
}
