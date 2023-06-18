package com.paulo.hotel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paulo.hotel.model.Quarto;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

	
//	@Query("from Quarto q LEFT JOIN q.reservas r WHERE (r.data != :data) OR r is null")
//	@Query("select q from Quarto q where q.id NOT IN (select q2.id from Quarto q2 JOIN q2.reservas r2 where r2.data=:data)")
	
	@Query(value = "select * from quarto  where  quarto.id not in "
			+ "(select quarto.id from quarto inner join "
			+ "reserva on quarto.id=reserva.quarto_id "
			+ "where reserva.data=:data)",
			countQuery = "select count(1) from quarto  where  quarto.id "
					+ "not in (select quarto.id from quarto inner join reserva "
					+ "on quarto.id=reserva.quarto_id where reserva.data=:data)",
					nativeQuery = true)
	Page<Quarto> quartosDisponiveisData(Date data,PageRequest pageRequest);

}
