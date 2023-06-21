package com.paulo.hotel.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


@Entity
public class Usuario {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1195143493435535734L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String username;
	private String password;

	private String nome;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tab_user_roles", joinColumns = @JoinColumn(name = "usuario_id"))
	@Column(name = "role_id")
	@org.hibernate.annotations.ForeignKey(name = "fk_usuario_role")
	private List<String> roles;

	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
}
