package com.paulo.hotel.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paulo.hotel.model.Quarto;

public class ReservaDTO {

	private Long id;
	
	@NotNull(message = "Insira a data para a reserva.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data;
	
	private Long quarto;
	@NotNull(message = "Insira o nome do responsavel pela reserva.")
	private String nome;
	
	public Long getQuarto() {
		return quarto;
	}
	public void setQuarto(Long quarto) {
		this.quarto = quarto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
