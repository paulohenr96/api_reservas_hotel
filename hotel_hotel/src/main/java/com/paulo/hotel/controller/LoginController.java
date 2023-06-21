package com.paulo.hotel.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paulo.hotel.dto.LoginDTO;
import com.paulo.hotel.dto.SessionDTO;
import com.paulo.hotel.dto.TokenDTO;
import com.paulo.hotel.exception.LoginNotFoundException;
import com.paulo.hotel.model.Usuario;
import com.paulo.hotel.repository.UserRepository;
import com.paulo.hotel.security.JWTConstants;
import com.paulo.hotel.security.JWTCreator;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("login")
@Api(tags="Efetuar o login.")
public class LoginController {

	private final UserRepository userRepository;

	public LoginController(UserRepository userRepository) {
		this.userRepository = userRepository;
		// TODO Auto-generated constructor stub
	}

	@PostMapping
	public ResponseEntity<SessionDTO> login(@RequestBody @Valid LoginDTO login) {
		SessionDTO sessao = new SessionDTO();

		Optional<Usuario> optional = userRepository.findByUsername(login.getUsername());
		if (optional.isEmpty() || !(new BCryptPasswordEncoder().matches(login.getPassword(),optional.get().getPassword()))) {
			throw new LoginNotFoundException();
		}

		Usuario usuario = optional.get();

		TokenDTO tokenInfo = new TokenDTO();
		tokenInfo.setSubject(usuario.getUsername());
		tokenInfo.setRoles(usuario.getRoles());
		Date dataExpiracao = new Date(System.currentTimeMillis() + JWTConstants.PERIOD);
		tokenInfo.setDateEnd(dataExpiracao);
		tokenInfo.setDateStart(new Date());
		String jwt = JWTCreator.createJWT(tokenInfo);

		sessao.setUsuario(usuario.getUsername());
		sessao.setToken(jwt);
		sessao.setDataExpiracao(dataExpiracao);

		return new ResponseEntity<SessionDTO>(sessao,HttpStatus.OK);

	}
}
