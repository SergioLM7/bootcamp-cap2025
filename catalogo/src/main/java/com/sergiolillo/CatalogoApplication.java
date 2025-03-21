package com.sergiolillo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.sergiolillo.domain.contracts.repositories.ActorRepository;
import com.sergiolillo.domain.contracts.repositories.CategoryRepository;
import com.sergiolillo.domain.contracts.services.CategoryService;
import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.domain.services.FilmServiceImpl;

import jakarta.transaction.Transactional;


@SpringBootApplication
@RestController
public class CatalogoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository dao;
	
	@Autowired
	private ActorRepository dao2;
	
	@Autowired
	private FilmServiceImpl serviceFilm;
	
	@Autowired
	private CategoryService serv;

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		//checkFilmWithAutor();
	}
	

	@Transactional()
	public void checkFilmWithAutor() {
		serviceFilm.getAll().forEach(System.err::println);

	}
	
	public void checkAutor() {
		var actor = new Actor(0, "PEPE", " ");
		if(actor.isValid()) {
			dao2.save(actor);
		} else {
			System.err.println(actor.getErrorsMessage());
		}
	}

}