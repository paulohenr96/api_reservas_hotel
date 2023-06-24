package com.paulo.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paulo.hotel.dto.TipoDTO;
import com.paulo.hotel.model.Tipo;


@Repository
public interface TipoRepository extends JpaRepository<Tipo,Long> {

	@Query("From Tipo t where t.nome=:nome")
	Optional<Tipo> findTipoByNome(String nome);

}
