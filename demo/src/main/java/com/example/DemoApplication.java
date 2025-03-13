package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domain.contracts.repositories.ActoresRepository;
import com.example.domain.entities.Actor;
import com.example.ioc.Rango;
import com.example.ioc.Repositorio;
import com.example.ioc.Servicio;
import com.example.util.Calculadora;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private ActoresRepository dao;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	//Inyecta un Servicio en la variable srv al arrancar la aplicación
	//Al estar dentro de la app, ya está dentro del contexto de inyección
	@Autowired
	Servicio srv;
	
	/* @Autowired
	 * @Qualifier("verdad")
	Repositorio repo1;
	@Autowired
	 * @Qualifier("mentira")
	Repositorio repo2;
	*/
	
	//@Autowired
	//Repositorio repo;
	
	@Autowired
	Repositorio repo1;
	
	@Autowired
	Repositorio repo2;

	@Value("${mi.valor: Este es el valor por defecto}")
	String valor;
	
	@Autowired
	Rango rango;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada");
		//ejemplosIOC();
		//ejemplosDePruebas();
		ejemplosDatos();
	}
	
	//Consultas CRUD a la BBDD para la tabla Actores
	private void ejemplosDatos() {
//		var item = dao.findById(202);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setFirstName("Juan");
//			actor.setLastName(actor.getLastName().toUpperCase());
//			dao.save(actor);
//		} else {
//			System.err.println("No se ha encontrado al actor.");
//		}
		
		//Si no existe la instancia en la BBDD, la crea, si existe, la REEMPLAZA
		//Si no tiene clave, genera el insert, y si tiene clave, la busca para ver si hace update
			//var actor = new Actor(0, "Pepe", "Galindo");
			//dao.save(actor);
		
		//Busca todos los Actores y los imprime en pantalla
		//dao.findAll().forEach(System.err::println);
		
//		dao.deleteById(202);
//		dao.findAll().forEach(System.err::println);
		
		
		//dao.findTop100ByFirstNameStartingWithOrderByLastNameDesc("P").forEach(System.err::println);
		//dao.findTop100ByFirstNameStartingWith("P", Sort.by("FirstName").ascending()).forEach(System.err::println);

		dao.findByActorIdGreaterThan(100).forEach(System.err::println);
	}
	
	private void ejemplosIOC() {
		//Servicio srv = new Servicio(new Repositorio(new Configuracion()));
		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

//		srv.guardar();
//		repo.guardar();
		repo1.guardar();
		repo2.guardar();
		System.err.println("Valor: " + valor);
		System.err.println("Rango: " + rango);
	}
	
	private void ejemplosDePruebas() {
//		var calc = new Calculadora();
//		System.out.print("Suma: " + calc.suma(2, 3));
		
	}
	/*
	 * @Bean
	 * CommandLineRunner demo(){
	 * 	return (args) -> {
	 * 		System.err.println("Aplicación arrancadaaa");
	 * 	}
	 * }
	 * 
	 */
}
