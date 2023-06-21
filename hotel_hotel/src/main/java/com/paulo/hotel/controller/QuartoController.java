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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("quartos")
@Api(tags = "Operações CRUD relacionadas aos quartos.")
public class QuartoController {

	private final QuartoService quartoService;

	public QuartoController(QuartoService quartoService) {
		this.quartoService = quartoService;
		// TODO Auto-generated constructor stub
	}

	@PostMapping
	@ApiOperation("Cadastrar Um Novo Quarto")
	public ResponseEntity<String> salvarQuarto(@ApiParam("Quarto novo") @Valid @RequestBody QuartoDTO quarto) {
		return new ResponseEntity<String>(quartoService.salvarQuarto(quarto), HttpStatus.OK);

	}

	
	
	@DeleteMapping("{idQuarto}")
	@ApiOperation("Remove um quarto")
	public ResponseEntity<String> deletarQuarto(@ApiParam("ID do quarto") @PathVariable Long idQuarto) {
		return new ResponseEntity<String>(quartoService.deletarQuarto(idQuarto), HttpStatus.OK);

	}

	@GetMapping
	@ApiOperation("Todos os quartos")
	public ResponseEntity<Page<QuartoDTO>> findAllQuartos(@RequestParam(name="page",defaultValue = "0") Integer page,
														@RequestParam(name="size",defaultValue = "5")Integer size) {
		return new ResponseEntity<Page<QuartoDTO>>(quartoService.findAllQuartos(PageRequest.of(page,size)), HttpStatus.OK);

	}
	@GetMapping("/disponiveis/")
	@ApiOperation("Todos os quartos disponiveis na data")
	public ResponseEntity<Page<QuartoDTO>> findAllQuartosDisponiveis(@ApiParam("Data no formato dd/MM/yyyy") @RequestParam(name="data",required = true) String data,
												@RequestParam(name="page",defaultValue = "0")Integer page,
												@RequestParam(name="size",defaultValue = "5")Integer size) {
		return new ResponseEntity<Page<QuartoDTO>>(quartoService.findAllQuartosDisponiveis(data,page,size), HttpStatus.OK);

	}

	@GetMapping("{idQuarto}")
	@ApiOperation("Retorna um quarto em específico.")
	public ResponseEntity<QuartoDTO> getQuartoById(@ApiParam("ID do quarto") @PathVariable Long idQuarto) {
		return new ResponseEntity<QuartoDTO>(quartoService.getQuartoById(idQuarto), HttpStatus.OK);

	}

	@PutMapping("{idQuarto}")
	@ApiOperation("Atualiza o quarto")
	public ResponseEntity<String> atualizarQuarto(@ApiParam("ID do quarto") @PathVariable Long idQuarto,@ApiParam("Novas informações do quarto") @RequestBody QuartoDTO novoQuarto) {
		return new ResponseEntity<String>(quartoService.atualizarQuarto(idQuarto,novoQuarto), HttpStatus.OK);

	}
	

	
	
}
