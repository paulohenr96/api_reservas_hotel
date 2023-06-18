package com.paulo.hotel.exception.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.paulo.hotel.dto.ErroDTO;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.QuartoReservadoException;
import com.paulo.hotel.exception.ReservaNotFoundException;
import com.paulo.hotel.exception.UserNotFoundException;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler{

	

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		ErroDTO erros=new ErroDTO();
		erros.setStatus(status.value());
		erros.setErro(ex.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.toList()));
		return new ResponseEntity<>(erros,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErroDTO> handleUserNotFoundException(UserNotFoundException e,HttpServletResponse r){
		
		ErroDTO erros=new ErroDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return new ResponseEntity<>(erros,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ReservaNotFoundException.class)
	public ResponseEntity<ErroDTO> handleReservaNotFoundException(ReservaNotFoundException e,HttpServletResponse r){
		
		ErroDTO erros=new ErroDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return new ResponseEntity<>(erros,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(QuartoReservadoException.class)
	public ResponseEntity<ErroDTO> handleQuartoReservadoException(QuartoReservadoException e,HttpServletResponse r){
		
		ErroDTO erros=new ErroDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return new ResponseEntity<>(erros,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(QuartoNotFoundException.class)
	public ResponseEntity<ErroDTO> handleQuartoNotFoundException(QuartoNotFoundException e,HttpServletResponse r){
		
		ErroDTO erros=new ErroDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return new ResponseEntity<>(erros,HttpStatus.NOT_FOUND);
	}
	
}
