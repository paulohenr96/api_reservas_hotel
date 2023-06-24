package com.paulo.hotel.mapper;

import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.model.Tipo;

public class TipoMapper {

	public static Tipo toEntity(TipoDTO dto) {
		Tipo tipo = new Tipo();

		tipo.setNome(dto.getNome());
		tipo.setPreco(dto.getPreco());

		return tipo;
	}

	public static TipoDTO toDTO(Tipo entity) {
		TipoDTO tipo = new TipoDTO();

		tipo.setNome(entity.getNome());
		tipo.setPreco(entity.getPreco());

		return tipo;
	}
}
