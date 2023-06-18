package com.paulo.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.UserNotFoundException;
import com.paulo.hotel.mapper.Mapper;
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
	QuartoDTO quartoDTO;
	List<Quarto> lista;
	Page<Quarto> pagina;

	@BeforeEach
	public void setUp() {
		quarto = new Quarto(1L);
		quartoDTO=new QuartoDTO(1L);
		lista = List.of(new Quarto(2L), new Quarto(3L), new Quarto(5L));
		pagina = new PageImpl<>(lista);
	}

	
	//Testando o findbyid do service
	@Test
	void buscarQuartosPeloIdComSucesso() {
		
		//Estabelecendo o optional que vai retornar do banco de dados
		when(quartoRepository.findById(2L)).thenReturn(Optional.of(quarto));
		
		//pegnado a saida real do service
		QuartoDTO quartoById = service.getQuartoById(2L);
		
		//verificando se a saida real é igual a saida ideal
		assertEquals(quartoById, Mapper.quartoToDTO(quarto));

		verify(quartoRepository).findById(2L);
//		
		verifyNoMoreInteractions(quartoRepository);
	}

	
	//Testando o findaAll
	@Test
	void buscarTodosQuartosComSucesso() {

		//Estabelecendo a saida do bd.
		when(quartoRepository.findAll(PageRequest.of(0, 3))).thenReturn(pagina);
		
		//Ppegando a saida do service.
		Page<QuartoDTO> findAllQuartos = service.findAllQuartos(PageRequest.of(0, 3));
		
		//Verificando se a saida real é igual a saida esperada.
		assertEquals(findAllQuartos, pagina.map(Mapper::quartoToDTO));

		//Verificando se o metodo foi chamado apenas 1 vez.
		verify(quartoRepository).findAll(PageRequest.of(0, 3));
		verifyNoMoreInteractions(quartoRepository);
	}

	
	//Testando o metodo deletar
	@Test
	void deletarQuartoComSucesso() {
		//Estabelçecendo a saida do banco de dados
		when(quartoRepository.existsById(1L)).thenReturn(true);
		
		//pegando a saida real do service
		String saidaReal = service.deletarQuarto(1L);

		String saidaEsperada="";
		verify(quartoRepository).deleteById(1L);

		assertEquals(saidaEsperada, saidaReal);
	}

	//Testando se a exceção jogada é correta.(QuartoNotFoundException)
	@Test
	void deletarQuartoNaoExistente() {
		when(quartoRepository.existsById(1L)).thenReturn(false);
		assertThrows(QuartoNotFoundException.class, () -> service.deletarQuarto(1L));

	}

	
	//Testando o metodo findById
	@Test
	void findByIdQuartoComSucesso() {
		Long idQuarto = 1L;

		
		
		when(quartoRepository.findById(idQuarto)).thenReturn(Optional.of(quarto));

		
		
		QuartoDTO saidaReal = service.getQuartoById(idQuarto);
		assertEquals(quartoDTO, saidaReal);

		verify(quartoRepository).findById(idQuarto);
	}

	
	//Verificando a execção jogada(QuartoNotFoundException)
	@Test
	void findByIdQuartoException() {
		Long idQuarto = 1L;

		when(quartoRepository.findById(idQuarto)).thenReturn(Optional.empty());

		assertThrows(QuartoNotFoundException.class, () -> service.getQuartoById(idQuarto));

		verify(quartoRepository).findById(idQuarto);
	}

	@Test
	void findAllQuartosDisponiveisSucesso() throws ParseException {
		String data = "28/11/2023";
		Date parse = new SimpleDateFormat("dd/MM/yyyy").parse(data);

		
		List<QuartoDTO> contentEsperado = lista.stream().map(Mapper::quartoToDTO).collect(Collectors.toList());
		
		//Pagina que vai vir do banco de dados
		Page<Quarto> pagina = new PageImpl<>(lista);
		
		//Pagina que é pra sair do service (dto)
		Page<QuartoDTO> saidaEsperada = new PageImpl<>(contentEsperado);

		
		//Configurando bd
		when(quartoRepository.quartosDisponiveisData(parse, PageRequest.of(0, 3))).thenReturn(pagina);

		//Saida real do service
		Page<QuartoDTO> saidaReal = service.findAllQuartosDisponiveis(data, 0, 3);
		
		//Verificando se o conteudo esperado é igual ao coteudo real.
		assertEquals(contentEsperado,saidaReal.getContent());
		
		//Verificando se a pagina esperada é igual a pagina real
		assertEquals(saidaEsperada,saidaReal);

		verify(quartoRepository).quartosDisponiveisData(parse, PageRequest.of(0, 3));

	}
	
	@Test
	void findAllQuartosDisponiveisDataInvalida()  {
		//Colocando data invalida para dar erro
		String data = "22342348/11/2023";

		

		//Testando se a exceção foi correta.
		assertThrows(RuntimeException.class,()->service.findAllQuartosDisponiveis(data, 0, 5));
		

	}
	
	
	
}
