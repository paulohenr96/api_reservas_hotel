package com.paulo.hotel.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.paulo.hotel.dto.ReservaDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.QuartoReservadoException;
import com.paulo.hotel.exception.ReservaNotFoundException;
import com.paulo.hotel.mapper.Mapper;
import com.paulo.hotel.model.Quarto;
import com.paulo.hotel.model.Reserva;
import com.paulo.hotel.repository.QuartoRepository;
import com.paulo.hotel.repository.ReservaRepository;
import com.paulo.hotel.service.ReservaService;

@RestClientTest
public class ReservaServiceTest {

	@InjectMocks
	ReservaService service;

	@Mock
	ReservaRepository reservaRepository;
	@Mock
	QuartoRepository quartoRepository;

	Reserva reserva;
	ReservaDTO reservaDTO;

	List<Reserva> lista;
	List<ReservaDTO> listaDTO;

	Page<Reserva> pagina;
	Page<ReservaDTO> paginaDTO;

	@BeforeEach
	void setUp() {
		reserva = new Reserva(2L);
		reservaDTO = new ReservaDTO(2L);

		lista = List.of(new Reserva(1L), new Reserva(2L), new Reserva(3L));

		listaDTO = List.of(new ReservaDTO(1L), new ReservaDTO(2L), new ReservaDTO(3L));

		pagina = new PageImpl<>(lista);
		paginaDTO = new PageImpl<>(listaDTO);

	}

	@Test
	void novaReserva() {

		Date data = new Date();
		Long idQuarto = 2L;

		reservaDTO.setData(data);
		reservaDTO.setNome("Nome Teste");

		
		
		when(reservaRepository.existsReserva(idQuarto,data)).thenReturn(false);
		when(quartoRepository.findById(idQuarto)).thenReturn(Optional.of(new Quarto(idQuarto)));

		service.novaReserva(reservaDTO, idQuarto);

		verify(reservaRepository).existsReserva(idQuarto,data);
		verify(quartoRepository).findById(anyLong());
		verify(reservaRepository).save(any(Reserva.class));

	}

	@Test
	void novaReservaException() {

		Date data = new Date();
		Long idQuarto = 2L;

		reservaDTO.setData(data);
		reservaDTO.setNome("Nome Teste");

		when(reservaRepository.existsReserva(idQuarto,data)).thenReturn(true);
		when(quartoRepository.findById(idQuarto)).thenReturn(Optional.of(new Quarto(idQuarto)));

		assertThrows(QuartoReservadoException.class, () -> service.novaReserva(reservaDTO, idQuarto));

	}

	@Test
	void getAllComSucesso() {
		List<ReservaDTO> contentEsperado = new ArrayList<>();
		contentEsperado.add(new ReservaDTO(1L));
		contentEsperado.add(new ReservaDTO(2L));
		contentEsperado.add(new ReservaDTO(3L));

		Page<ReservaDTO> saidaEsperada = new PageImpl<>(contentEsperado);
		List<Reserva> lista = new ArrayList<>();
		lista.add(new Reserva(1L));
		lista.add(new Reserva(2L));
		lista.add(new Reserva(3L));

		Page<Reserva> pagina = new PageImpl<>(lista);

		when(reservaRepository.findAll(PageRequest.of(0, 3))).thenReturn(pagina);

		Page<ReservaDTO> saidaReal = service.getAll(PageRequest.of(0, 3));

		assertEquals(contentEsperado, saidaReal.getContent());
		assertEquals(saidaEsperada, saidaReal);

		verify(reservaRepository).findAll(PageRequest.of(0, 3));

	}

	@Test
	void findByIdSucesso() {
		Long id = 1L;

		ReservaDTO saidaEsperada = new ReservaDTO(id);

		when(reservaRepository.findById(id)).thenReturn(Optional.of(new Reserva(id)));

		ReservaDTO findReservaById = service.findReservaById(id);

		assertEquals(saidaEsperada, findReservaById);
		verify(reservaRepository).findById(id);
	}

	@Test
	void findByIdException() {
		Long id = 1L;

		ReservaDTO saidaEsperada = new ReservaDTO(id);

		when(reservaRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ReservaNotFoundException.class, () -> service.findReservaById(id));

	}

	@Test
	void deleteReservaSucesso() {

		Long id = 2L;

		when(reservaRepository.existsById(id)).thenReturn(true);
		service.deleteReserva(id);

		verify(reservaRepository).deleteById(id);
	}

	@Test
	void deleteReservaThrowsReservaNotFoundException() {

		Long id = 2L;

		when(reservaRepository.existsById(id)).thenReturn(false);
		assertThrows(ReservaNotFoundException.class, () -> service.deleteReserva(id));

	}

	@Test
	void findAllByDataSucesso() {
		String data = "28/10/2023";
		when(reservaRepository.findAll(any(Example.class), any(PageRequest.class))).thenReturn(pagina);
		Page<ReservaDTO> saidaReal = service.getAll(data, PageRequest.of(0, 3));
		assertEquals(paginaDTO, saidaReal);

		verify(reservaRepository).findAll(any(Example.class), any(PageRequest.class));

	}

	@Test
	void getAllByQuartoSucesso() {

		Long idQuarto = 2L;
		when(reservaRepository.findAll(any(Example.class), any(PageRequest.class))).thenReturn(pagina);

		Page<ReservaDTO> saidaReal = service.getAllByQuarto(idQuarto, PageRequest.of(0, 3));

		assertEquals(paginaDTO, saidaReal);
		verify(reservaRepository).findAll(any(Example.class), any(PageRequest.class));
		verifyNoMoreInteractions(reservaRepository);
	}

	@Test
	void atualizaReservaInexistenteThrowRunTimeException() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO(2L);
		when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class,()->service.atualizaReserva(idReserva, reservaNova));
	}
	@Test
	void atualizaReservaExistenteThrowRunTimeException() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO(2L);
		when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(new Reserva(2L)));
		assertThrows(RuntimeException.class,()->service.atualizaReserva(idReserva, reservaNova));
	}
	@Test
	void atualizaReservaExistenteComSucesso() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO();
		reservaNova.setQuarto(1L);
		reservaNova.setData(new Date());
		Reserva reservaAntiga=new Reserva(idReserva);
		reservaAntiga.setQuarto(new Quarto(2L));
		
		
		when(reservaRepository.existsReserva(1L,reservaNova.getData())).thenReturn(false);

		when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(reservaAntiga));
		when(quartoRepository.findById(anyLong())).thenReturn(Optional.of(new Quarto(1L)));
		
		
		String saidaEsperada="Operacao Realizada com Sucesso";
		String saidaReal = service.atualizaReserva(idReserva, reservaNova);
		
		assertEquals(saidaEsperada,saidaReal);
		verify(quartoRepository).findById(1L);
		verify(reservaRepository).findById(2L);
		verify(reservaRepository).save(new Reserva(2L));

		
	}
	
	@Test
	void atualizaReservaInexistenteComSucesso() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO();
		reservaNova.setQuarto(1L);
		reservaNova.setData(new Date());
		Reserva reservaSalva=new Reserva();
		reservaSalva.setQuarto(new Quarto(1L));
		
		
		when(reservaRepository.existsReserva(1L,reservaNova.getData())).thenReturn(false);

		when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(quartoRepository.findById(anyLong())).thenReturn(Optional.of(new Quarto(1L)));
		
		
		String saidaEsperada="Operacao Realizada com Sucesso";
		String saidaReal = service.atualizaReserva(idReserva, reservaNova);
		
		
		assertEquals(saidaEsperada,saidaReal);
		verify(quartoRepository).findById(1L);
		verify(reservaRepository).findById(2L);
		verify(reservaRepository).save(reservaSalva);

		
	}
	
	@Test
	void atualizaReservaInexistenteThrowsQuartoNotFoundException() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO();
		reservaNova.setQuarto(1L);
		
		
		when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(quartoRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		
		assertThrows(QuartoNotFoundException.class,()->service.atualizaReserva(idReserva, reservaNova));
		
		

		
	}
	@Test
	void atualizaReservaExistenteThrowsQuartoNotFoundException() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO();
		reservaNova.setQuarto(1L);
		
		Reserva reservaAntiga=new Reserva(2L);
		reservaAntiga.setQuarto(new Quarto(2L));
		

		when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(reservaAntiga));
		when(quartoRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		
		assertThrows(QuartoNotFoundException.class,()->service.atualizaReserva(idReserva, reservaNova));
		
		

		
	}
	
	@Test
	void atualizaReservaExistenteThrowsQuartoReservadoException() {
		Long idReserva=2L;
		ReservaDTO reservaNova=new ReservaDTO();
		reservaNova.setQuarto(1L);
		reservaNova.setData(new Date());
		Reserva reservaAntiga=new Reserva(idReserva);
		reservaAntiga.setQuarto(new Quarto(2L));
		
		when(reservaRepository.existsReserva(1L,reservaNova.getData())).thenReturn(true);
		when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(quartoRepository.findById(anyLong())).thenReturn(Optional.of(new Quarto(2L)));
		
		
		assertThrows(QuartoReservadoException.class,()->service.atualizaReserva(idReserva, reservaNova));
		verify(reservaRepository).existsReserva(1L,reservaNova.getData());
		verify(reservaRepository).findById(idReserva);
		verify(quartoRepository).findById(1L);
		verifyNoMoreInteractions(reservaRepository);


		
	}
	
	
}
