package com.paulo.hotel.controller;

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

import com.paulo.hotel.dto.QuartoDTO;
import com.paulo.hotel.service.QuartoService;

@Controller
@RequestMapping("quartos")
public class QuartoController {

	private final QuartoService quartoService;

	public QuartoController(QuartoService quartoService) {
		this.quartoService = quartoService;
		// TODO Auto-generated constructor stub
	}

	@PostMapping
	public ResponseEntity<String> salvarQuarto(@Valid @RequestBody QuartoDTO quarto) {
		return new ResponseEntity<String>(quartoService.salvarQuarto(quarto), HttpStatus.OK);

	}

	@DeleteMapping("{idQuarto}")
	public ResponseEntity<String> deletarQuarto(@PathVariable Long idQuarto) {
		return new ResponseEntity<String>(quartoService.deletarQuarto(idQuarto), HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<Page<QuartoDTO>> findAllQuartos(@RequestParam(name="page",defaultValue = "0") Integer page,
														@RequestParam(name="size",defaultValue = "5")Integer size) {
		return new ResponseEntity<Page<QuartoDTO>>(quartoService.findAllQuartos(PageRequest.of(page,size)), HttpStatus.OK);

	}
	@GetMapping("/disponiveis/")
	public ResponseEntity<Page<QuartoDTO>> findAllQuartosDisponiveis(@RequestParam(name="data",required = true) String data,
												@RequestParam(name="page",defaultValue = "0")Integer page,
												@RequestParam(name="size",defaultValue = "5")Integer size) {
		return new ResponseEntity<Page<QuartoDTO>>(quartoService.findAllQuartosDisponiveis(data,page,size), HttpStatus.OK);

	}

	@GetMapping("{idQuarto}")
	public ResponseEntity<QuartoDTO> getQuartoById(@PathVariable Long idQuarto) {
		return new ResponseEntity<QuartoDTO>(quartoService.getQuartoById(idQuarto), HttpStatus.OK);

	}

	@PutMapping("{idQuarto}")
	public ResponseEntity<String> atualizarQuarto(@PathVariable Long idQuarto,@RequestBody QuartoDTO novoQuarto) {
		return new ResponseEntity<String>(quartoService.atualizarQuarto(idQuarto,novoQuarto), HttpStatus.OK);

	}
	

	
	
}
