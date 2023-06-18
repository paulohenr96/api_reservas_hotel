package com.paulo.hotel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.repository.QuartoRepository;
import com.paulo.hotel.service.QuartoService;

@ExtendWith(MockitoExtension.class)
public class QuartoServiceTest {
	
	@InjectMocks
	QuartoService service;
	
	
	@Mock
	QuartoRepository quartoRepository;

	
	Quarto quarto;
	List<Quarto> lista;
	@BeforeEach
	public void setUp() {
		quarto=new Quarto(2L);
		
		lista=List.of(new Quarto(2L),new Quarto(3L), new Quarto(5L));
	}
	
	@Test
	void buscarQuartosPeloIdComSucesso() {
		when(quartoRepository.findById(2L)).thenReturn(Optional.of(quarto));
		Quarto quartoById = service.getQuartoById(2L);
		assertEquals(quartoById,quarto);
		
		verify(quartoRepository).findById(2L);
//		
		verifyNoMoreInteractions(quartoRepository);
	}


	@Test
	void buscarTodosQuartosComSucesso() {

		when(quartoRepository.findAll()).thenReturn(lista);
		List<QuartoDTO> findAllQuartos = service.findAllQuartos(PageRequest.of(1, 3)).getContent();
		assertEquals(findAllQuartos,lista);
		
		verify(quartoRepository).findAll();
		verifyNoMoreInteractions(quartoRepository);
	}
}


