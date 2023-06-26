package com.paulo.hotel.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import com.paulo.hotel.exception.TipoNotFoundException;
import com.paulo.hotel.exception.UserNotFoundException;
import com.paulo.hotel.mapper.Mapper;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Reserva;
import com.paulo.hotel.model.Tipo;
import com.paulo.hotel.repository.QuartoRepository;
import com.paulo.hotel.repository.ReservaRepository;
import com.paulo.hotel.repository.TipoRepository;

@Service
public class ReservaService {

	private final ReservaRepository reservaRepository;
	private final QuartoRepository quartoRepository;
	private final TipoRepository tipoRepository;

	public ReservaService(ReservaRepository reservaRepository, QuartoRepository quartoRepository,
			TipoRepository tipoRepository) {
		this.reservaRepository = reservaRepository;
		// TODO Auto-generated constructor stub
		this.quartoRepository = quartoRepository;
		this.tipoRepository = tipoRepository;
	}

	public void novaReserva(ReservaDTO reserva, Long idQuarto) {
		// TODO Auto-generated method stub

		if (reservaRepository.existsReserva(idQuarto, reserva.getCheckinDate(), reserva.getCheckoutDate())) {
			throw new QuartoReservadoException(idQuarto);
		}
		;

		Reserva novaReserva = Mapper.dtotoReserva(reserva);
		Quarto quarto = quartoRepository.findById(idQuarto).orElseThrow(() -> new QuartoNotFoundException(idQuarto));
		novaReserva.setQuarto(quarto);

		BigDecimal valor = calculaValorReserva(quarto.getTipo());
		novaReserva.setValor(valor);
		reservaRepository.save(novaReserva);

	}

	public BigDecimal calculaValorReserva(String tipo) {
		return tipoRepository.findTipoByNome(tipo).get().getPreco();
	}

	public Page<ReservaDTO> getAll(PageRequest pageRequest) {
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
		LocalDate data = LocalDate.now();
		data = LocalDate.parse(dataTxt, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Reserva res = new Reserva();
		res.setCheckinDate(data);
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
