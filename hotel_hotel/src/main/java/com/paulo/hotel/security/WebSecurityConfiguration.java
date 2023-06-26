package com.paulo.hotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.paulo.hotel.filter.JWTFilter;
import com.paulo.hotel.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final UsuarioService usuarioService;
	
	public WebSecurityConfiguration(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
		// TODO Auto-generated constructor stub
	}
	private static final String[] SWAGGER_WHITELIST = { "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-ui/**" };

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth
		
		.userDetailsService(usuarioService)
		
		.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		http.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/login","/login/").permitAll()
		.antMatchers(SWAGGER_WHITELIST).permitAll()
		.antMatchers("/quartos","/quartos/**").hasAnyRole("ADMINISTRADOR")
		.antMatchers("/tipos","/tipos/**").hasAnyRole("ADMINISTRADOR")
		.anyRequest().authenticated().and()
//		.addFilterAfter(new JWTFilter(),UsernamePasswordAuthenticationFilter.class)
		.logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.and().httpBasic();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
