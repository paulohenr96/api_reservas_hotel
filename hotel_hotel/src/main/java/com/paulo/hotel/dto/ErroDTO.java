package com.paulo.hotel.dto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;

import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Reserva;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ErroDTO {

	private int status;

	private List<String> erro;

	public ErroDTO(int status, String erro) {
		super();
		this.status = status;
		this.erro = Arrays.asList(erro);
	}

}
