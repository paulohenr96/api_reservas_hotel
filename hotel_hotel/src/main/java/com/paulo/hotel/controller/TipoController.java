package com.paulo.hotel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.service.TipoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping("tipo")
public class TipoController {

	private final TipoService service;
	
	public TipoController(TipoService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<String> salvarTipo(@RequestBody TipoDTO tipo){
		return new ResponseEntity<String>("",HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<TipoDTO>> findAll(){
		return new ResponseEntity<List<TipoDTO>>(service.getAllTipo(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<TipoDTO> findTipoByNome(@RequestParam(name = "nome") String nome){
		return new ResponseEntity<TipoDTO>(service.getTipoByNome(nome),HttpStatus.CREATED);
	}
	@GetMapping("{id}")
	public ResponseEntity<TipoDTO> findTipoById(@PathVariable Long tipo){
		
		return new ResponseEntity<TipoDTO>(service.findTipoById(tipo),HttpStatus.FOUND);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTipo(@PathVariable Long tipo){
		service.deleteTipo(tipo);
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<String> updateTipo(@PathVariable Long id,@Valid @RequestBody TipoDTO tipo){
		return new ResponseEntity<String>("",service.updateTipo(id, tipo));
		
	}
}
