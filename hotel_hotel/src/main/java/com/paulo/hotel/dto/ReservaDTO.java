package com.paulo.hotel.dto;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paulo.hotel.model.Quarto;

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

	@NotNull(message = "Insira a data para a reserva.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data;
	private Long quarto;
	@NotNull(message = "Insira o nome do responsavel pela reserva.")
	private String nome;

	public ReservaDTO(Long l) {
		// TODO Auto-generated constructor stub
		this.id = l;
	}

}
