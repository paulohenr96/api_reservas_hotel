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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.service.TipoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("tipos")
@Api(tags="Operações CRUD relacionadas aos tipos de quartos.")
public class TipoController {

	private final TipoService service;
	
	public TipoController(TipoService service) {
		this.service = service;
	}
	
	
	@ApiOperation("Inserir novo tipo.")
	@PostMapping
	public ResponseEntity<Void> salvarTipo(@Valid @RequestBody TipoDTO tipo){
		service.saveTipo(tipo);
		return  ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@ApiOperation("Ver todos os tipos.")
	@GetMapping
	public ResponseEntity<List<TipoDTO>> findAll(){
		return new ResponseEntity<List<TipoDTO>>(service.getAllTipo(),HttpStatus.OK);
	}
	
	
	@ApiOperation("Consultar tipo.")
	@GetMapping("/consultanome")
	public ResponseEntity<TipoDTO> findTipoByNome(@RequestParam(name = "nome") String nome){
		return new ResponseEntity<TipoDTO>(service.getTipoByNome(nome),HttpStatus.FOUND);
	}
	
	
	@ApiOperation("Consultar tipo.")
	@GetMapping("{id}")
	public ResponseEntity<TipoDTO> findTipoById(@PathVariable Long id){
		
		return new ResponseEntity<TipoDTO>(service.findTipoById(id),HttpStatus.FOUND);
	}
	
	
	@ApiOperation("Deletar tipo.")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTipo(@PathVariable Long id){
		service.deleteTipo(id);
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	
	
	@ApiOperation("Editar tipo.")
	@PutMapping("{id}")
	public ResponseEntity<String> updateTipo(@PathVariable Long id,@Valid @RequestBody TipoDTO tipo){
		return new ResponseEntity<String>("",service.updateTipo(id, tipo));
		
	}
}
