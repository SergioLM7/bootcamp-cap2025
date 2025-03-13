package com.sergiolillo;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiolillo.domain.contracts.services.CategoryService;
import com.sergiolillo.domain.entities.Category;
import com.sergiolillo.domain.entities.FilmCategory;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;

@SpringBootApplication
@RestController
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		checkCategory();
	}
	
	@Autowired
	private CategoryService serv;
	
	public void checkCategory() {
		//serv.getAll().forEach(System.err::println);
		//System.err.println(serv.getOne(15));
	    try {
			serv.add(new Category(0, "Prueba3", new ArrayList<FilmCategory>()));
			serv.getAll().forEach(System.err::println);
			
		} catch (DuplicateKeyException e) {
			System.err.println(e);
			e.printStackTrace();
		} catch (InvalidDataException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }

}