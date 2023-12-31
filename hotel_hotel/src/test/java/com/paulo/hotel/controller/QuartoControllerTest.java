package com.paulo.hotel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.security.RunAs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.repository.QuartoRepository;
import com.paulo.hotel.service.QuartoService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class QuartoControllerTest {
	
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	QuartoService service;

	@Autowired
	QuartoRepository repository;
	
	@Test
	void salvarQuartoComSucesso() throws Exception {
		
		when(service.salvarQuarto(any(QuartoDTO.class))).thenReturn("ok");
		
		QuartoDTO quartoDTO = new QuartoDTO();
		quartoDTO.setCamas(4);
		quartoDTO.setTipo("tipo");
		
		mockMvc.perform(post("/quartos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quartoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
		
	}
	
	@Test
	void deletarQuartoSucesso() throws Exception{
		
		Long id=5L;


//		when(service.deletarQuarto(anyLong())).thenReturn("");
		
		
		mockMvc.perform(delete("/quartos/"+id)).andExpect(status().isOk());
		
	}
	
	@Test
	void findAllQuartosSucesso() throws Exception{	
		List<QuartoDTO> lista=List.of(new QuartoDTO(2L),
				new QuartoDTO(3L),
				new QuartoDTO(4L));
		Page<QuartoDTO> pagina=new PageImpl<>(lista);
		
		when(service.findAllQuartos(any(PageRequest.class))).thenReturn(pagina);
		
		
		
		mockMvc.perform(get("/quartos")
				.param("page", "0")
				.param("size","5"))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.content").isArray());
		
	}
	
	@Test
	void findAllQuartosDisponiveisSucesso() throws Exception{	
		List<QuartoDTO> lista=List.of(new QuartoDTO(2L),
				new QuartoDTO(3L),
				new QuartoDTO(4L));
		Page<QuartoDTO> pagina=new PageImpl<>(lista);
		
		String data="28/10/2023";
		String dataOut="29/10/2023";

		when(service.findAllQuartosDisponiveis(data,dataOut,0,3)).thenReturn(pagina);
		
		
		
		mockMvc.perform(get("/quartos/disponiveis/")
				.param("checkinDate", data)
				.param("checkoutDate", dataOut)

				.param("page", "0")
				.param("size","3"))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.content").isArray());
		
	}
	
	@Test
	void findAllQuartoByIdSucesso() throws Exception{	
		List<QuartoDTO> lista=List.of(new QuartoDTO(2L),
				new QuartoDTO(3L),
				new QuartoDTO(4L));
		Page<QuartoDTO> pagina=new PageImpl<>(lista);
		
		String data="28/10/2023";
		when(service.getQuartoById(anyLong())).thenReturn(new QuartoDTO(2L));
		
		
		
		mockMvc.perform(get("/quartos/1"))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.id").value(2));
		
	}
	@Test
	void atualizaQuartoComSucesso() throws Exception{	
		Long id=1L;
		QuartoDTO quartoParaAtualizar=new QuartoDTO();
		
		quartoParaAtualizar.setCamas(4);
		quartoParaAtualizar.setTipo("normal");
		
		
		when(service.atualizarQuarto(id, quartoParaAtualizar)).thenReturn("Operacao realizada com sucesso.");
		
		mockMvc.perform(put("/quartos/"+id)
				.contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quartoParaAtualizar)))
				.andExpect(status().isOk())
	            .andExpect(content().string("Operacao realizada com sucesso."));
		
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
