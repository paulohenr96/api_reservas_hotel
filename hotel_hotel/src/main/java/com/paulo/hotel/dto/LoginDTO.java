package com.paulo.hotel.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {

	@NotNull(message="Insert a username")
	private String username;
	@NotNull(message="Insert a password")
	private String password;
}
