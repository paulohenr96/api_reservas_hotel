package com.paulo.hotel.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paulo.hotel.dto.ReservaDTO;
import com.paulo.hotel.service.ReservaService;

@Controller
@RequestMapping("reservas")
public class ReservaController {

	private final ReservaService reservaService;
	
	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
		// TODO Auto-generated constructor stub
	}
	
	
	
	@PostMapping
	public ResponseEntity<String> novaReserva(@RequestParam Long idQuarto, @Valid @RequestBody ReservaDTO reserva){
		reservaService.novaReserva(reserva,idQuarto);
		return ResponseEntity.ok("");
	}
	
	
	
	
	@GetMapping
	public ResponseEntity<Page<ReservaDTO>> getAllReservas(@RequestParam(name="page",defaultValue = "0") Integer page,
															@RequestParam(name="size",defaultValue="5") Integer size){
		return ResponseEntity.ok(reservaService.getAll(PageRequest.of(page,size)));
	}
	
	
	
	
	@GetMapping("/pordata/")
	public ResponseEntity<Page<ReservaDTO>> getAllReservasByData(
			@RequestParam(name = "data") String data,
			@RequestParam(name="page",defaultValue = "0") Integer page,
			@RequestParam(name="size",defaultValue="5") Integer size){
		return ResponseEntity.ok(reservaService.getAll(data,PageRequest.of(page,size)));
	}
	
	
	
	
	@GetMapping("/porquarto/{quarto}")
	public ResponseEntity<Page<ReservaDTO>> getAllReservasByQuarto(@PathVariable Long quarto,
			@RequestParam(name="page",defaultValue = "0") Integer page,
			@RequestParam(name="size",defaultValue="5") Integer size){
		return ResponseEntity.ok(reservaService.getAllByQuarto(quarto,PageRequest.of(page,size)));
	}
	
	
	
	@GetMapping("{idReserva}")
	public ResponseEntity<ReservaDTO> findByIdReserva(@PathVariable Long idReserva){
		return ResponseEntity.ok(reservaService.findReservaById(idReserva));
	}
	
	
	
	
	@DeleteMapping("{idReserva}")
	public ResponseEntity<String> deleteReserva(@PathVariable Long idReserva){
		reservaService.deleteReserva(idReserva);
		return ResponseEntity.ok("");
	}
	
	
	
	
	@PutMapping("{idReserva}")
	public ResponseEntity<String> atualizaReserva(@PathVariable Long idReserva,
												@RequestBody ReservaDTO reservaNova){
		return new ResponseEntity<String>(reservaService.atualizaReserva(idReserva,
									reservaNova),HttpStatus.OK);	
	}

}
  