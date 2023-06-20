package com.paulo.hotel.mapper;

import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.dto.ReservaDTO;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Reserva;

public class Mapper {

	public static ReservaDTO reservaToDTO(Reserva reserva) {

		ReservaDTO reservaDTO = new ReservaDTO();
		if (reserva.getId() != null) {
			reservaDTO.setId(reserva.getId());
		}
		if (reserva.getQuarto()!=null) {
			reservaDTO.setQuarto(reserva.getQuarto().getId());

		}
		if (reserva.getData()!=null) {
			reservaDTO.setData(reserva.getData());

		}
		if (reserva.getNome()!=null && !reserva.getNome().trim().isEmpty()) {
			reservaDTO.setNome(reserva.getNome());

		}

		return reservaDTO;
	}

	public static Reserva dtotoReserva(ReservaDTO reservaDTO) {

		Reserva reserva = new Reserva();
		if (reservaDTO.getId() != null) {
			reserva.setId(reservaDTO.getId());
		}
		if (reservaDTO.getQuarto()!=null) {
			reserva.setQuarto(new Quarto(reservaDTO.getQuarto()));

		}
		reserva.setData(reservaDTO.getData());
		if (reservaDTO.getNome()!=null && !reservaDTO.getNome().trim().isEmpty()) {
			reserva.setNome(reservaDTO.getNome());

		}
		return reserva;
	}
	
	public static QuartoDTO quartoToDTO(Quarto quarto) {
		 QuartoDTO quartoDTO = new QuartoDTO();
		 quartoDTO.setCamas(quarto.getCamas());
		 quartoDTO.setId(quarto.getId());
		 quartoDTO.setTipo(quarto.getTipo());
		 return quartoDTO;
	}
	
	public static Quarto dtoToQuarto(QuartoDTO quartoDTO) {
		
		Quarto quarto=new Quarto();
		if (quartoDTO.getId()!=null) {
			quarto.setId(null);

		}
		quarto.setTipo(quartoDTO.getTipo());
		quarto.setCamas(quartoDTO.getCamas());
		
		return quarto; 
	}
}
