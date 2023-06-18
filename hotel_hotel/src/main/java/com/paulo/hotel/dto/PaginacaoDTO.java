package com.paulo.hotel.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PaginacaoDTO<T> extends PageImpl<T>{

	
	

	public PaginacaoDTO(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9167250809974358941L;

	
}
