package com.paulo.hotel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.model.Tipo;
import com.paulo.hotel.service.TipoService;
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TipoControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TipoService service;
	
	String nome="";
	BigDecimal valor=BigDecimal.valueOf(90.5);
	
	void setUp() {
		nome="paulo";
		valor=BigDecimal.valueOf(90.5);
	}
	
	@Test
	void salvarRetonarCreated() throws Exception {
		TipoDTO tipo=new TipoDTO("nome",BigDecimal.valueOf(90));
		mockMvc.perform(post("/tipos").contentType(MediaType.APPLICATION_JSON).content(asJsonString(tipo))).andExpect(status().isCreated());
		
	}
	
	@Test
	void findAllStatusOk() throws Exception {
		
		 List<TipoDTO> tipos = new ArrayList<>();
	        tipos.add(new TipoDTO("Tipo 1",BigDecimal.valueOf(90)));
	        tipos.add(new TipoDTO("Tipo 2",BigDecimal.valueOf(60)));
	        
	        when(service.getAllTipo()).thenReturn(tipos);
		
		mockMvc.perform(get("/tipos")).andExpect(jsonPath("$[0].nome").value("Tipo 1")).andExpect(status().isOk());

	}
	
	@Test
	void retornarTipoUsandoNomeStatusFound() throws Exception {
		
		TipoDTO tipo=new TipoDTO(nome,valor);
        when(service.getTipoByNome(anyString())).thenReturn(tipo);
		mockMvc.perform(get("/tipos/consultanome").param("nome", nome)).andExpect(jsonPath("$.nome").value(nome)).andExpect(status().isFound());

	}
	
	@Test
	void retornarTipoUsandoIDStatusFound() throws Exception {
		Long id=1L;
		TipoDTO tipo=new TipoDTO(1L,nome,valor);
        when(service.findTipoById(anyLong())).thenReturn(tipo);
		mockMvc.perform(get("/tipos/"+id)).andExpect(jsonPath("$.nome").value(nome)).andExpect(status().isFound());

	}
	
	@Test
	void deletarUsandoIdRetornaOk() throws Exception {
		Long id=1L;
		mockMvc.perform(delete("/tipos/"+id)).andExpect(content().string("")).andExpect(status().isOk());

	}
	
	@Test
	void updateTipoRetornaCreated() throws Exception {
		Long id=1L;
		TipoDTO tipo=new TipoDTO(nome,valor);
		
        when(service.updateTipo(anyLong(),any(TipoDTO.class))).thenReturn(HttpStatus.CREATED);

		mockMvc.perform(put("/tipos/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(tipo))).andExpect(status().isCreated());

	}
	
	@Test
	void updateTipoRetornaFound() throws Exception {
		Long id=1L;
		TipoDTO tipo=new TipoDTO(nome,valor);
		
        when(service.updateTipo(anyLong(),any(TipoDTO.class))).thenReturn(HttpStatus.FOUND);

		mockMvc.perform(put("/tipos/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(tipo))).andExpect(status().isFound());

	}
	
	
	private static String asJsonString(Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
