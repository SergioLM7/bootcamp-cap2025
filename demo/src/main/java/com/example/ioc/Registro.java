package com.example.ioc;

import org.springframework.stereotype.Component;

@Component
public class Registro {

	public Registro(Configuracion config) {
		this.config = config;
	}
	
	
	private Configuracion config;
	
	//public Registro() {
		//System.err.println("Registro creado...");
	//}

	

}
