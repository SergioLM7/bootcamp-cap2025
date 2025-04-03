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

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.transaction.Transactional;


@SpringBootApplication
@RestController
@OpenAPIDefinition(
		info = @Info(title = "Microservice:Film Catalogue",  
		version = "1.0",                
		description = "Microservice of a catalogue of films and actors similar to Netflix.",                
		license = @License(name = "Apache 2.0", 
		url = "https://www.apache.org/licenses/LICENSE-2.0.html"),                
		contact = @Contact(name = "Sergio Lillo", 
		url = "https://github.com/SergioLM7", 
		email = "sergiolillom@gmail.com")
		),
		externalDocs = @ExternalDocumentation(description = "Project Documentation", url = "https://github.com/SergioLM7/bootcamp-cap2025/tree/main/catalogo")
)
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