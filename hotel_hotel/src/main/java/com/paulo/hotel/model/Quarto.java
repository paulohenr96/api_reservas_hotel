package com.paulo.hotel.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.paulo.hotel.dto.QuartoDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor
public class Quarto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	
	@Column(nullable = false)
	private Integer camas;
	
	@Column(nullable = false)
	private String tipo;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Reserva> reservas;
	
	public Quarto(Long id) {
		this.id = id;
	}

	
	
}
