package com.paulo.hotel.repository;

import java.time.LocalDate;

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
	
//	@Query(value = "select * from quarto  where  quarto.id not in "
//			+ "(select quarto.id from quarto inner join "
//			+ "reserva on quarto.id=reserva.quarto_id "
//			+ "where reserva.data=:data)",
//			countQuery = "select count(1) from quarto  where  quarto.id "
//					+ "not in (select quarto.id from quarto inner join reserva "
//					+ "on quarto.id=reserva.quarto_id where reserva.data=:data)",
//					nativeQuery = true)

	@Query(value="select * from quarto where quarto.id "
			+ "NOT IN"
			+ " (select quarto_id from reserva where ('2022-11-29' >= checkin_date  AND '2022-11-29'< checkout_date) "
			+ "			or ('2022-11-30' > checkin_date  AND '2022-11-30'<= checkout_date) )",
			nativeQuery = true,
			countQuery = "(select count(1) from quarto "
					+ "where quarto.id "
					+ "NOT IN (select quarto_id from reserva where (:dataIn >= checkin_date  AND :dataIn< checkout_date) "
					+ "or (:dataOut > checkin_date  AND :dataOut<= checkout_date) )) ")
	
	Page<Quarto> quartosDisponiveisData(LocalDate dataIn,LocalDate dataOut,PageRequest pageRequest);

}
