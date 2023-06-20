package com.paulo.hotel.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class QuartoDTO {

	private Long id;

	@NotNull(message="Insira a quantidade de camas")
	private Integer camas;
	
	@NotNull(message="Insira o tipo do quarto")
	private String tipo;

	public QuartoDTO(Long l) {
		// TODO Auto-generated constructor stub
		this.id=l;
	}
	

	
}
