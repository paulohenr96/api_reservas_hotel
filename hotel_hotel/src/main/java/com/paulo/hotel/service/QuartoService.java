package com.paulo.hotel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.convert.JodaTimeConverters.DateToLocalDateConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.stereotype.Component;

import com.paulo.hotel.dto.PaginacaoDTO;
import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.dto.ReservaDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.UserNotFoundException;
import com.paulo.hotel.mapper.Mapper;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Reserva;
import com.paulo.hotel.repository.QuartoRepository;

@Component
public class QuartoService {

	private final QuartoRepository quartoRepository;
	
	public QuartoService(QuartoRepository quartoRepository) {
		this.quartoRepository = quartoRepository;
		// TODO Auto-generated constructor stub
	}
	
	public String salvarQuarto(QuartoDTO quarto) {
		// TODO Auto-generated method stub
		
		quartoRepository.save(Mapper.dtoToQuarto(quarto));
		return "ok";
	}

	public String deletarQuarto(Long idQuarto) {
		// TODO Auto-generated method stub
		if (!quartoRepository.existsById(idQuarto)) {throw new QuartoNotFoundException(idQuarto);}
		try {
		quartoRepository.deleteById(idQuarto);
		}catch(Exception e) {
			throw new RuntimeException("Erro ao deletar o quarto => "+e.getMessage());
		}
		return "";
	}

	public Page<QuartoDTO> findAllQuartos(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return quartoRepository.findAll(pageRequest).map(Mapper::quartoToDTO);
	}

	public Quarto getQuartoById(Long idQuarto) {
		// TODO Auto-generated method stub
		
		
		return quartoRepository.findById(idQuarto)
				.orElseThrow(()-> new QuartoNotFoundException(idQuarto));
	}

	public String atualizarQuarto(Long idQuarto) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<QuartoDTO> findAllQuartosDisponiveis(String data,int page, int size) {
		// TODO Auto-generated method stub
		
		
		Date parse=new Date();
		try {
			parse = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(String.format("Data %s está no formato invalido", data));
		}
		
		
		
		Page<QuartoDTO> map = quartoRepository.quartosDisponiveisData(parse,PageRequest.of(page, size))
		.map(Mapper::quartoToDTO);
		return map;
	}

	

}
