package com.paulo.hotel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.hotel.dto.ReservaDTO;
import com.paulo.hotel.service.ReservaService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ReservaService service;
	
	@Test
	void novaReservaSucesso() throws Exception{
		
		ReservaDTO reserva=new ReservaDTO(2L);
		reserva.setData(new Date());
		reserva.setNome("Paulo");

		Long id=1L;
		mockMvc.perform(post("/reservas")
				.contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reserva))
				.param("idQuarto", id+""))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
//		verify(service).novaReserva(reserva,id );
	
	}
	
	@Test
	void findAllReservasSucesso() throws Exception{
		List<ReservaDTO> lista=List.of(
				new ReservaDTO(1L),
				new ReservaDTO(2L),
				new ReservaDTO(3L));
		
		Page<ReservaDTO> pagina = new PageImpl<>(lista);
		
		when(service.getAll(any(PageRequest.class))).thenReturn(pagina);
		
		mockMvc.perform(get("/reservas")
				.param("size", "3")
				.param("page", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray());
	}
	
	@Test
	void findAllReservasPorDataSucesso() throws Exception{
		List<ReservaDTO> lista=List.of(
				new ReservaDTO(1L),
				new ReservaDTO(2L),
				new ReservaDTO(3L));
		
		Page<ReservaDTO> pagina = new PageImpl<>(lista);
		
		
		String data="28/10/2023";
		when(service.getAll(anyString(),any(PageRequest.class))).thenReturn(pagina);
		
		mockMvc.perform(get("/reservas/pordata/")
				.param("data", data)
				.param("size", "3")
				.param("page", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray());
	}
	@Test
	void findAllReservasPorQuartoSucesso() throws Exception{
		List<ReservaDTO> lista=List.of(
				new ReservaDTO(1L),
				new ReservaDTO(2L),
				new ReservaDTO(3L));
		
		Page<ReservaDTO> pagina = new PageImpl<>(lista);
		
		
		Long id=1L;
		when(service.getAllByQuarto(anyLong(),any(PageRequest.class))).thenReturn(pagina);
		
		mockMvc.perform(get("/reservas/porquarto/"+id)
				.param("size", "3")
				.param("page", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray());
	}
	@Test
	void findByIdReservaSucesso() throws Exception{
		
		
		
		Long id=1L;
		when(service.findReservaById(anyLong())).thenReturn(new ReservaDTO(id));
		
		mockMvc.perform(get("/reservas/"+id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id));
	}
	@Test
	void deleteByIdSucesso() throws Exception{
		
		
		
		Long id=1L;
		
		mockMvc.perform(delete("/reservas/"+id))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		verify(service).deleteReserva(id);
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
