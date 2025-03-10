package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Rango;
import com.example.ioc.Repositorio;
import com.example.ioc.Servicio;
import com.example.util.Calculadora;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

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
		ejemplosDePruebas();
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
