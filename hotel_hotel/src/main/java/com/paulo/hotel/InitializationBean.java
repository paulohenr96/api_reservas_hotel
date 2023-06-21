package com.paulo.hotel;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

//@Component
public class InitializationBean {

//	@PostConstruct
	public void init() {
		System.out.println("======Iniciou a aplicação=======");
	}
}
