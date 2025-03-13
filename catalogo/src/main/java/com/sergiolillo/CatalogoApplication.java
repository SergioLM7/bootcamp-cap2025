package com.sergiolillo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiolillo.domain.contracts.services.CategoryService;

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
		serv.getAll().forEach(System.err::println);;
	}
	
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }

}
