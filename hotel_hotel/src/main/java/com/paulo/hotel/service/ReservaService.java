package com.paulo.hotel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.paulo.hotel.dto.ReservaDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.QuartoReservadoException;
import com.paulo.hotel.exception.ReservaNotFoundException;
import com.paulo.hotel.exception.UserNotFoundException;
import com.paulo.hotel.mapper.Mapper;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Reserva;
import com.paulo.hotel.repository.QuartoRepository;
import com.paulo.hotel.repository.ReservaRepository;

@Service
public class ReservaService {

	private final ReservaRepository reservaRepository;
	private final QuartoRepository quartoRepository;

	public ReservaService(ReservaRepository reservaRepository, QuartoRepository quartoRepository) {
		this.reservaRepository = reservaRepository;
		// TODO Auto-generated constructor stub
		this.quartoRepository = quartoRepository;
	}

	public void novaReserva(ReservaDTO reserva, Long idQuarto) {
		// TODO Auto-generated method stub
		Reserva reservaExample = new Reserva();

		reservaExample.setData(reserva.getData());
		reservaExample.setQuarto(new Quarto(idQuarto));

		ExampleMatcher exampleMatcher = ExampleMatcher.matching();
		Example<Reserva> example = Example.of(reservaExample, exampleMatcher);

		boolean existsReserva = reservaRepository.exists(example);

		if (existsReserva) {
			throw new QuartoReservadoException(idQuarto);
		}

		Reserva novaReserva = Mapper.dtotoReserva(reserva);
		Quarto quarto = quartoRepository.findById(idQuarto).orElseThrow(() -> new QuartoNotFoundException(idQuarto));
		novaReserva.setQuarto(quarto);
		reservaRepository.save(novaReserva);

	}

	public Page<ReservaDTO> getAll(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return reservaRepository.findAll(pageRequest).map(Mapper::reservaToDTO);
	}

	public ReservaDTO findReservaById(Long id) {

		return reservaRepository.findById(id).map(Mapper::reservaToDTO)
				.orElseThrow(() -> new ReservaNotFoundException(id));

	}

	public void deleteReserva(Long id) {

		if (!reservaRepository.existsById(id)) {
			throw new ReservaNotFoundException(id);
		}
		reservaRepository.deleteById(id);
	}

	public Page<ReservaDTO> getAll(String dataTxt, PageRequest pageRequest) {
		Date data = new Date();
		try {
			data = new SimpleDateFormat("dd/MM/yyyy").parse(dataTxt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(String.format("A data %s e invalida", dataTxt));
		
		}

		Reserva res = new Reserva();
		res.setData(data);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("data", GenericPropertyMatchers.exact());

		Example<Reserva> example = Example.of(res, exampleMatcher);
		return reservaRepository.findAll(example, pageRequest).map(Mapper::reservaToDTO);
	}

	public Page<ReservaDTO> getAllByQuarto(Long id, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		Reserva res = new Reserva();
		res.setQuarto(new Quarto(id));
		ExampleMatcher exampleMatcher = ExampleMatcher.matching();

		Example<Reserva> example = Example.of(res, exampleMatcher);
		return reservaRepository.findAll(example, pageRequest).map(Mapper::reservaToDTO);
	}
}
