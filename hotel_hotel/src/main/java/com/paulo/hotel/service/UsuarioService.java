package com.paulo.hotel.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paulo.hotel.repository.UserRepository;

@Service
public class UsuarioService implements UserDetailsService{

	private final UserRepository userRepository; 
	
	
	public UsuarioService(UserRepository userRepository) {
		this.userRepository = userRepository;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username)
				.map(e-> new User(e.getUsername(),
							e.getPassword(),
							e.getRoles()
								.stream()
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList())))
				.orElseThrow(()->new UsernameNotFoundException(username));
	}

}
