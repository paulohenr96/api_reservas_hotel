package com.paulo.hotel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.mapper.Mapper;
import com.paulo.hotel.model.Quarto;
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
		if (!quartoRepository.existsById(idQuarto)) {
			throw new QuartoNotFoundException(idQuarto);
		}
		try {
			quartoRepository.deleteById(idQuarto);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar o quarto => " + e.getMessage());
		}
		return "";
	}

	public Page<QuartoDTO> findAllQuartos(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return quartoRepository.findAll(pageRequest).map(Mapper::quartoToDTO);
	}

	public QuartoDTO getQuartoById(Long idQuarto) {
		// TODO Auto-generated method stub

		return quartoRepository.findById(idQuarto).map(Mapper::quartoToDTO)
				.orElseThrow(() -> new QuartoNotFoundException(idQuarto));
	}

	public String atualizarQuarto(Long idQuarto, QuartoDTO novoQuarto) {
		// TODO Auto-generated method stub

		Quarto quartoNovo = Mapper.dtoToQuarto(novoQuarto);

		quartoRepository.findById(idQuarto).map(e -> {
			e.setCamas(quartoNovo.getCamas());
			e.setTipo(quartoNovo.getTipo());
			return quartoRepository.save(e);
		}).orElse(quartoRepository.save(quartoNovo));

		return "Operacao realizada com sucesso.";
	}

	public Page<QuartoDTO> findAllQuartosDisponiveis(String data, int page, int size) {
		// TODO Auto-generated method stub

		Date parse = new Date();
		try {
			parse = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(String.format("Data %s está no formato invalido", data));
		}

		Page<QuartoDTO> map = quartoRepository.quartosDisponiveisData(parse, PageRequest.of(page, size))
				.map(Mapper::quartoToDTO);
		return map;
	}
	
	

}
