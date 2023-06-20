package com.paulo.hotel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paulo.hotel.model.Reserva;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{

	
	List<Reserva> findAllByData(Date data);

	@Query(value = "select (select count(1)  from reserva where quarto_id=:idQuarto AND data=:data) >0 as boolean",nativeQuery = true)
	boolean existsReserva(Long idQuarto, Date data);
	
}
