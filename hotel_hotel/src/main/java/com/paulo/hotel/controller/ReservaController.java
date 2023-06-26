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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("reservas")
@Api(tags = "Operações CRUD relacionada às reservas de quartos.")
public class ReservaController {

	private final ReservaService reservaService;
	
	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
		// TODO Auto-generated constructor stub
	}
	
	
	
	@PostMapping
	@ApiOperation("Cadastrar nova reserva.")
	public ResponseEntity<String> novaReserva(
			@RequestParam(name = "idQuarto") Long idQuarto,
			@ApiParam("Nova reserva.") @Valid @RequestBody ReservaDTO reserva){
		reservaService.novaReserva(reserva,idQuarto);
		return ResponseEntity.ok("");
	}
	
	
	
	
	@GetMapping
	@ApiOperation("Lista de todas as reservas.")
	public ResponseEntity<Page<ReservaDTO>> getAllReservas(@RequestParam(name="page",defaultValue = "0") Integer page,
															@RequestParam(name="size",defaultValue="5") Integer size){
		return ResponseEntity.ok(reservaService.getAll(PageRequest.of(page,size)));
	}
	
	
	
	
	@GetMapping("/pordata/")
	@ApiOperation("Lista de todas as reservas em determinada data.")
	public ResponseEntity<Page<ReservaDTO>> getAllReservasByData(
			 @RequestParam(name = "data") String data,
			@RequestParam(name="page",defaultValue = "0") Integer page,
			@RequestParam(name="size",defaultValue="5") Integer size){
		return ResponseEntity.ok(reservaService.getAll(data,PageRequest.of(page,size)));
	}
	
	
	
	
	@GetMapping("/porquarto/{quarto}")
	@ApiOperation("Lista de todas as reservas de determinado quarto.")
	public ResponseEntity<Page<ReservaDTO>> getAllReservasByQuarto(@ApiParam("ID do quarto para ver as reservas.") @PathVariable Long quarto,
			@RequestParam(name="page",defaultValue = "0") Integer page,
			@RequestParam(name="size",defaultValue="5") Integer size){
		return ResponseEntity.ok(reservaService.getAllByQuarto(quarto,PageRequest.of(page,size)));
	}
	
	
	
	@GetMapping("{idReserva}")
	@ApiOperation("Consultar alguma reserva em específico de acordo com o ID.")
	public ResponseEntity<ReservaDTO> findByIdReserva(@PathVariable Long idReserva){
		return ResponseEntity.ok(reservaService.findReservaById(idReserva));
	}
	
	
	
	
	@DeleteMapping("{idReserva}")
	@ApiOperation("Deletar alguma reserva.")
	public ResponseEntity<String> deleteReserva(@PathVariable Long idReserva){
		reservaService.deleteReserva(idReserva);
		return ResponseEntity.ok("");
	}
	
	
	
	

}
  