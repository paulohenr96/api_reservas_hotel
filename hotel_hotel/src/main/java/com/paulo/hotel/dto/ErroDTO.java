package com.paulo.hotel.dto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ErroDTO {
	
	private int status;
	
	private List<String> erro;

	
	
	
	
	
	
	public ErroDTO(int status, List<String> erro) {
		super();
		this.status = status;
		this.erro = erro;
	}
	public ErroDTO(int status, String erro) {
		super();
		this.status = status;
		this.erro = Arrays.asList(erro);
	}

	public ErroDTO() {
		// TODO Auto-generated constructor stub
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getErro() {
		return erro;
	}

	public void setErro(List<String> erro) {
		this.erro = erro;
	}
	
	
	
	

}
