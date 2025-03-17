package com.sergiolillo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiolillo.domain.contracts.repositories.ActoresRepository;
import com.sergiolillo.domain.contracts.repositories.CategoryRepository;
import com.sergiolillo.domain.contracts.services.CategoryService;
import com.sergiolillo.domain.entities.Actor;


@SpringBootApplication
@RestController
public class CatalogoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository dao;
	
	@Autowired
	private ActoresRepository dao2;

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		checkAutor();
		//checkCategory();
	}
	
	@Autowired
	private CategoryService serv;
	
	public void checkAutor() {
		var actor = new Actor(0, "PEPE", " ");
		if(actor.isValid()) {
			dao2.save(actor);
		} else {
			System.err.println(actor.getErrorsMessage());
		}
	}
	public void checkCategory() {

		serv.getAll().forEach(System.err::println);
		//System.err.println(serv.getOne(15));
	   /* try {
			serv.add(new Category(0, "Prueba3", new ArrayList<FilmCategory>()));
			serv.getAll().forEach(System.err::println);
			
		} catch (DuplicateKeyException e) {
			System.err.println(e);
			e.printStackTrace();
		} catch (InvalidDataException e) {
			System.err.println(e);
			e.printStackTrace();
		}*/
		/*try {
			serv.deleteById(18);
			serv.getAll().forEach(System.err::println);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}*/
		//dao.findNovedades().forEach(System.err::println);
		//dao.findTop15ByNameStartingWithOrderByNameDesc("A").forEach(System.err::println);
	}
	
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }

}