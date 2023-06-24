package com.paulo.hotel.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TipoDTO {
	private Long id;
	
	private String nome;
	
	
	private BigDecimal preco;
	
}
