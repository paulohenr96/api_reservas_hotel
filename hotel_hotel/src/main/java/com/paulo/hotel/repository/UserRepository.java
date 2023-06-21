package com.paulo.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paulo.hotel.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

	
	@Query(value = "from Usuario u where u.username=:username")
	Optional<Usuario> findByUsername(String username);

	
	@Query(value="from Usuario u where u.username=:username AND u.password=:password")
	Optional<Usuario> findLogin(String username, String password);
}
