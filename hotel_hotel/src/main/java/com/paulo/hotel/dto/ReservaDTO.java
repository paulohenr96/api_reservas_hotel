package com.paulo.hotel.dto;

import java.util.Date;
import java.util.Objects;

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
	
	public ReservaDTO(Long l) {
		// TODO Auto-generated constructor stub
		this.id=l;
	}
	public ReservaDTO() {
		// TODO Auto-generated constructor stub
	}
	

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
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservaDTO other = (ReservaDTO) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
