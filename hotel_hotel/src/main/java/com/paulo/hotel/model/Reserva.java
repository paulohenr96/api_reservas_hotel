package com.paulo.hotel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.paulo.hotel.dto.QuartoDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;

	
	@ManyToOne
	private Quarto quarto;
	
	private String nome;
	
	public Reserva(Long l) {
		// TODO Auto-generated constructor stub
		this.id=l;
	}


	
}
