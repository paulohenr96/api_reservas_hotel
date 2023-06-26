package com.paulo.hotel.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoDTO {
	


	private Long id;
	@NotNull(message="Insira o nome deste tipo de quarto")
	private String nome;
	
	@NotNull(message="Insira o valor da diaria deste tipo de quarto")
	private BigDecimal preco;
	public TipoDTO(String nome, BigDecimal preco) {
		// TODO Auto-generated constructor stub
		this.nome=nome;
		this.preco=preco;
	}
	
}
