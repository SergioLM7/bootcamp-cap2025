package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

//@Repository
//@Qualifier("mentira")
public class RepositorioMock implements Repositorio {
	
	public RepositorioMock() {
		//TODO Auto-generated constructor
	}
	
	@Override
	public void guardar() {
		System.err.println("Guardando el repositorio de mentira...");
	}
	
}
 