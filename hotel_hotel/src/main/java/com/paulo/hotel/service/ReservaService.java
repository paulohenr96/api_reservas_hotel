package com.paulo.hotel.service;

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

		if (reservaRepository.existsReserva(idQuarto, reserva.getCheckinDate(),reserva.getCheckoutDate())) {
			throw new QuartoReservadoException(idQuarto);
		}
		;
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
		 LocalDate data = LocalDate.now();
//		try {
//			data = new SimpleDateFormat("dd/MM/yyyy").parse(dataTxt);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			throw new RuntimeException(String.format("A data %s e invalida", dataTxt));
//
//		}
		 data=LocalDate.parse(dataTxt,DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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

	public String atualizaReserva(Long idReserva, ReservaDTO reservaNova) {

		Optional<Reserva> optional = reservaRepository.findById(idReserva);
		Reserva reserva = new Reserva();

		if (optional.isPresent()) {
			reserva = optional.get();
			reserva.setCheckinDate(reservaNova.getCheckinDate());
			reserva.setNome(reservaNova.getNome());
			if (reservaNova.getQuarto() != null && reservaNova.getQuarto() != reserva.getQuarto().getId()) {
				Long quarto = reservaNova.getQuarto();
				

				reserva.setQuarto(
						quartoRepository.findById(quarto).orElseThrow(() -> new QuartoNotFoundException(quarto)));
				
				if (reservaRepository.existsReserva(quarto, reserva.getCheckinDate(),reserva.getCheckoutDate())) {
					throw new QuartoReservadoException(quarto);
				}

			}

		} else {
			reserva = Mapper.dtotoReserva(reservaNova);
			Long quarto = reserva.getQuarto().getId();

			
			reserva.setQuarto(quartoRepository.findById(quarto).orElseThrow(() -> new QuartoNotFoundException(quarto)));
			if (reservaRepository.existsReserva(quarto, reserva.getCheckinDate(),reserva.getCheckoutDate())) {
				throw new QuartoReservadoException(quarto);
			};
		}

		if (reserva.getQuarto() == null) {
			throw new RuntimeException("Insira o quarto.");

		}
		reservaRepository.save(reserva);

		return "Operacao Realizada com Sucesso";
	}
}
