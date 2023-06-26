package com.paulo.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;

import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.exception.TipoNotFoundException;
import com.paulo.hotel.mapper.TipoMapper;
import com.paulo.hotel.model.Tipo;
import com.paulo.hotel.repository.TipoRepository;

@RestClientTest
public class TipoServiceTest {

	@Mock
	TipoRepository repo;

	@InjectMocks
	TipoService service;

	List<Tipo> lista;
	List<TipoDTO> listaDTO;

	@BeforeEach
	void setUp() {
		lista = List.of(new Tipo(1L, "suite", BigDecimal.valueOf(100)), new Tipo(2L, "deluxe", BigDecimal.valueOf(70)),
				new Tipo(3L, "simples", BigDecimal.valueOf(55)), new Tipo(4L, "flex", BigDecimal.valueOf(66)));
		listaDTO = lista.stream().map(TipoMapper::toDTO).collect(Collectors.toList());
	}

	@Test
	void salvarTipo() {
		TipoDTO tipo = new TipoDTO();
		tipo.setNome("Deluxe");
		tipo.setPreco(BigDecimal.valueOf(90.5));
		service.saveTipo(tipo);

		verify(repo).save(TipoMapper.toEntity(tipo));
	}

	@Test
	void findAllSucesso() {

		when(repo.findAll()).thenReturn(lista);
		List<TipoDTO> allTipo = service.getAllTipo();
		assertTrue(allTipo.stream().anyMatch(e -> e.getId() == 1L));
		assertTrue(allTipo.stream().anyMatch(e -> e.getId() == 2L));
		assertTrue(allTipo.stream().anyMatch(e -> e.getId() == 3L));

	}

	@Test
	void findAllVazioSucesso() {

		when(repo.findAll()).thenReturn(new ArrayList<Tipo>());
		List<TipoDTO> saida = service.getAllTipo();
		assertEquals(new ArrayList<TipoDTO>(), saida);

	}

	@Test
	void updateTipoOK() {
		Tipo tipo = new Tipo(1L, "Deluxe", BigDecimal.valueOf(50.5));

		Tipo tipo2 = new Tipo(null, "Super Deluxe", BigDecimal.valueOf(70.5));
		Long id = 1L;
		when(repo.findById(anyLong())).thenReturn(Optional.of(tipo));

		HttpStatus saida = service.updateTipo(id, TipoMapper.toDTO(tipo2));

		tipo2.setId(id);
		assertEquals(HttpStatus.OK, saida);
		verify(repo).save(tipo);

	}

	@Test
	void updateTipoCREATED() {

		Tipo tipo2 = new Tipo(null, "Super Deluxe", BigDecimal.valueOf(70.5));
		Long id = 1L;
		when(repo.findById(anyLong())).thenReturn(Optional.empty());

		HttpStatus saida = service.updateTipo(id, TipoMapper.toDTO(tipo2));

		assertEquals(HttpStatus.CREATED, saida);

	}

	@Test
	void deletarSemExcessao() {
		Long id = 1L;

		Tipo tipo = new Tipo(1L, "tipo", BigDecimal.valueOf(90.5));

		when(repo.existsById(id)).thenReturn(true);

		service.deleteTipo(id);
		verify(repo).deleteById(id);

	}

	@Test
	void deletarComExcessao() {
		Long id = 1L;

		Tipo tipo = new Tipo(1L, "tipo", BigDecimal.valueOf(90.5));

		when(repo.existsById(id)).thenReturn(false);

		assertThrows(TipoNotFoundException.class, () -> service.deleteTipo(id));

	}

	@Test
	void tipoByNomeSemExcessao() {
		String nome = "nome";
		Tipo tipo = new Tipo(1L, nome, BigDecimal.valueOf(90));
		when(repo.findTipoByNome(anyString())).thenReturn(Optional.of(tipo));

		TipoDTO saidaReal = service.getTipoByNome(nome);

		assertEquals(TipoMapper.toDTO(tipo), saidaReal);
	}

	@Test
	void tipoByNomeComExcessao() {
		String nome = "nome";
		when(repo.findTipoByNome(anyString())).thenReturn(Optional.empty());

		assertThrows(TipoNotFoundException.class, () -> service.getTipoByNome(nome));
	}

	@Test
	void acharTipoSemExcessao() {
		Long id = 1L;

		Tipo tipo = new Tipo(1L, "nome", BigDecimal.valueOf(90));

		when(repo.findById(anyLong())).thenReturn(Optional.of(tipo));

		TipoDTO saida = service.findTipoById(id);

		assertEquals(TipoMapper.toDTO(tipo), saida);
	}

	@Test
	void acharTipoComExcessao() {
		Long id = 1L;

		Tipo tipo = new Tipo(1L, "nome", BigDecimal.valueOf(90));

		when(repo.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(TipoNotFoundException.class, () -> service.findTipoById(id));

	}

}
