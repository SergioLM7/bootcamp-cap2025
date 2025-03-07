package com.example.ioc;

import org.springframework.stereotype.Service;

@Service
public class ServicioImpl implements Servicio {
	
	private Repositorio repositorio;
	
	public ServicioImpl(/*@Qualifier("mentira")*/Repositorio repositorio) {
		this.repositorio = repositorio;
	}
	
	@Override
	public void guardar() {
		//.. Interacciona con la BBDD y luego se lo manda al repositorio
		repositorio.guardar();
	}

}
