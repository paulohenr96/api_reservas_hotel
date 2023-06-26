package com.paulo.hotel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.stereotype.Component;

import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.TipoNotFoundException;
import com.paulo.hotel.mapper.Mapper;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Tipo;
import com.paulo.hotel.repository.QuartoRepository;
import com.paulo.hotel.repository.TipoRepository;

@Component
public class QuartoService {

	private final QuartoRepository quartoRepository;
	private final TipoRepository tipoRepository;

	public QuartoService(QuartoRepository quartoRepository, TipoRepository tipoRepository) {
		this.quartoRepository = quartoRepository;
		// TODO Auto-generated constructor stub
		this.tipoRepository = tipoRepository;
	}

	public String salvarQuarto(QuartoDTO quarto) {
		// TODO Auto-generated method stub
		
		Optional<Tipo> optional = tipoRepository.findTipoByNome(quarto.getTipo());
		if (optional.isEmpty()) {
			throw new TipoNotFoundException(quarto.getTipo());
		}
		
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
	boolean existeTipo(String tipo) {
		return tipoRepository.findTipoByNome(tipo).isPresent();
	}
	public String atualizarQuarto(Long idQuarto, QuartoDTO novoQuarto) {
		// TODO Auto-generated method stub

		Quarto quartoNovo = Mapper.dtoToQuarto(novoQuarto);
		Optional<Quarto> optional = quartoRepository.findById(idQuarto);
		if (optional.isPresent()) {
			Quarto quarto = optional.get();
			quarto.setCamas(quartoNovo.getCamas());
			
			boolean mudouTipo=quartoNovo.getTipo()!=quarto.getTipo();
			
			
			if (mudouTipo) {
				if(existeTipo(quartoNovo.getTipo())) {
					quarto.setTipo(quartoNovo.getTipo());
				}else {
					throw new TipoNotFoundException(quartoNovo.getTipo());
				}
			}
			
			quartoRepository.save(quarto);
		}
		else {quartoRepository.save(quartoNovo);}
		
		return "Operacao realizada com sucesso.";
	}

	public Page<QuartoDTO> findAllQuartosDisponiveis(String datain,String dataout, int page, int size) {
		// TODO Auto-generated method stub
		LocalDate checkinDate=LocalDate.parse(datain, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate checkoutDate=LocalDate.parse(dataout, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		

		Page<QuartoDTO> map = quartoRepository.quartosDisponiveisData(checkinDate,checkoutDate, PageRequest.of(page, size))
				.map(Mapper::quartoToDTO);
		return map;
	}
	
	

}
