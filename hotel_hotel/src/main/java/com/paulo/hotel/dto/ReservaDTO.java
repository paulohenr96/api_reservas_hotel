package com.paulo.hotel.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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

public class ReservaDTO {

	private Long id;

	@NotNull(message = "Insira a data para o checkin da reserva.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate checkinDate;
	
	@NotNull(message = "Insira a data para o checkout .")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate checkoutDate;

	

	private Long quarto;
	
	
	@NotNull(message = "Insira o nome do responsavel pela reserva.")
	private String nome;

	@NotNull(message = "Insira o valor da reserva.")
	private BigDecimal valor;
	
	public ReservaDTO(Long l) {
		// TODO Auto-generated constructor stub
		this.id = l;
	}

	public ReservaDTO(Long id, LocalDate checkinDate, Long quarto, String nome) {
		// TODO Auto-generated constructor stub
		
		this.id=id;
		this.checkinDate=checkinDate;
		this.quarto=quarto;
		this.nome=nome;
	}
	

}
