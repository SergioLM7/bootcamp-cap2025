package com.example.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PersonaTest {
	
	@Test
	void createPersona() {
		var p = new Persona(1, "Pepe", "García");
		
		//Si es null, ya no necesito comprobar lo demás
		assertNotNull(p);

		//Agrupo las aserciones una vez que el objeto no es null
		//El tercer argumento de la arrow function es el nombre para identificar la aserción
		assertAll("Constructor de Persona", 
				() -> assertEquals(2, p.id),
				() -> assertEquals("Pepe", p.nombre, "nombre"),
				() -> assertEquals("García", p.apellido, "apellido")

				);
		
		
	}

}