package com.paulo.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.exception.TipoNotFoundException;
import com.paulo.hotel.mapper.TipoMapper;
import com.paulo.hotel.model.Tipo;
import com.paulo.hotel.repository.TipoRepository;

@Service
public class TipoService {

	private final TipoRepository repository;

	public TipoService(TipoRepository repository) {
		this.repository = repository;
	}

	public void saveTipo(TipoDTO tipo) {
		repository.save(TipoMapper.toEntity(tipo));
	}

	public void deleteTipo(Long tipo) {
		if (!repository.existsById(tipo)) {throw new TipoNotFoundException(tipo);}
		repository.deleteById(tipo);
	}

	public List<TipoDTO> getAllTipo() {

		return repository.findAll()
				.stream()
				.map(TipoMapper::toDTO)
				.collect(Collectors.toList());
	}

	public TipoDTO getTipoByNome(String nome) {
		return repository.findTipoByNome(nome)
				.map(TipoMapper::toDTO)
				.orElseThrow(() -> new TipoNotFoundException(nome));
	}

	public HttpStatus updateTipo(Long id, TipoDTO tipo) {

		Optional<Tipo> optional = repository.findById(id);
		if (optional.isEmpty()) {
			repository.save(TipoMapper.toEntity(tipo));
			return HttpStatus.CREATED;
		}
		
		
		Tipo t = optional.get();
		t.setNome(tipo.getNome());
		t.setPreco(tipo.getPreco());
		
		repository.save(t);
		
		return HttpStatus.OK;

	}
	
	public TipoDTO findTipoById(Long tipo) {
		return repository.findById(tipo)
				.map(TipoMapper::toDTO)
				.orElseThrow(()->new TipoNotFoundException(tipo));
	}

}
