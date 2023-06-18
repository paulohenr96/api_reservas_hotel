package com.paulo.hotel.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	public QuartoDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCamas() {
		return camas;
	}

	public void setCamas(Integer camas) {
		this.camas = camas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		QuartoDTO other = (QuartoDTO) obj;
		return Objects.equals(id, other.id);
	}


}
