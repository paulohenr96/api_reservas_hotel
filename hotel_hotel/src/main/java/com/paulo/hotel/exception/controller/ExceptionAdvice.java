package com.paulo.hotel.exception.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.paulo.hotel.dto.ErroDTO;
import com.paulo.hotel.exception.LoginNotFoundException;
import com.paulo.hotel.exception.QuartoNotFoundException;
import com.paulo.hotel.exception.QuartoReservadoException;
import com.paulo.hotel.exception.ReservaNotFoundException;
import com.paulo.hotel.exception.UserNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler({ UserNotFoundException.class, ReservaNotFoundException.class, QuartoReservadoException.class,
			QuartoNotFoundException.class, LoginNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErroDTO handleNotFoundExceptions(Exception e) {
		return new ErroDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ErroDTO handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		return new ErroDTO(HttpStatus.NOT_ACCEPTABLE.value(), e.getRootCause().getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErroDTO handleException(Exception e) {
		return new ErroDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErroDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<String> erros = ex.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
		return new ErroDTO(HttpStatus.EXPECTATION_FAILED.value(), erros);
	}
}
