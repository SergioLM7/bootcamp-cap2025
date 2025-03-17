package com.sergiolillo.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FilmTest {
	
    private Film film;
    private FilmActor actor;
    private Language language;
    private FilmCategory category;

    @BeforeEach
    public void setUp() {
        language = new Language();
        language.setName("English");
        
        film = new Film(1, "Test Film", 120, "PG", (short) 2025, (byte) 7, new BigDecimal("4.99"), new BigDecimal("19.99"), "Test Title", language);
   
        actor = new FilmActor();
        actor.setActor(new Actor(1, "JOHNNY", "DEEP"));
        
        category = new FilmCategory();
        category.setCategory(new Category(1, "Action"));
        
    }

    @Test
    public void testConstructorFields() {
        film.addFilmCategory(category);
        film.addFilmActor(actor);
        film.setLanguage(language);
    	
    	assertNotNull(film, "El film no puede ser nulo");
    	
    	assertAll("Constructor de Film",
       () -> assertNotNull(film.getTitle(), "El título no debe ser nulo"),
       () -> assertEquals("Test Title", film.getTitle(), "El título debe coincidir con el valor pasado al constructor"),
       
       () ->  assertNotNull(film.getRentalRate(), "El rentalRate no debe ser nulo"),
       () -> assertEquals(new BigDecimal("4.99"), film.getRentalRate(), "El rentalRate debe coincidir con el valor pasado al constructor"),
      
       () -> assertNotNull(film.getLength(), "La longitud no debe ser nula"),
       () ->assertEquals(120, film.getLength(), "La longitud debe coincidir con el valor pasado al constructor"),
      
       () -> assertNotNull(film.getRating(), "La calificación no debe ser nula"),
       () -> assertEquals("PG", film.getRating(), "La calificación debe coincidir con el valor pasado al constructor"),

       () ->  assertNotNull(film.getLanguage(), "El idioma no debe ser nulo"),
       () -> assertEquals(language, film.getLanguage(), "El idioma debe coincidir con el que se asignó al constructor"),

       () -> assertNotNull(film.getFilmActors(), "La lista de actores no debe ser nula"),
       () -> assertFalse(film.getFilmActors().isEmpty(), "Debe haber al menos un actor en la lista"),

       () -> assertNotNull(film.getFilmCategories(), "La lista de categorías no debe ser nula"),
       () -> assertFalse(film.getFilmCategories().isEmpty(), "Debe haber al menos una categoría en la lista")
    			);
    	}

    @Test
    public void testAddFilmActor() {
        film.addFilmActor(actor);
        assertEquals(1, film.getFilmActors().size(), "Debe agregar un actor");
        assertTrue(film.getFilmActors().contains(actor), "El actor debe ser parte de la lista");
    }

    @Test
    public void testRemoveFilmActor() {
        film.addFilmActor(actor);
        film.removeFilmActor(actor);
        assertEquals(0, film.getFilmActors().size(), "Debe eliminar el actor");
        assertFalse(film.getFilmActors().contains(actor), "El actor ya no debe estar en la lista");
    }

    @Test
    public void testAddFilmCategory() {
        film.addFilmCategory(category);
        assertEquals(1, film.getFilmCategories().size(), "Debe agregar una categoría");
        assertTrue(film.getFilmCategories().contains(category), "La categoría debe estar en la lista");
    }

    @Test
    public void testRemoveFilmCategory() {
        film.addFilmCategory(category);
        film.removeFilmCategory(category);
        assertEquals(0, film.getFilmCategories().size(), "Debe eliminar la categoría");
        assertFalse(film.getFilmCategories().contains(category), "La categoría ya no debe estar en la lista");
    }
    
    @Test
    public void testAddActorWhenFilmActorsIsNull() {
        // Inicializamos la película con la lista de actores como null
        film.getFilmActors().add(null);

        assertThrows(NullPointerException.class, () -> {
            film.addFilmActor(actor);
        }, "La lista de actores no puede ser nula");
    }

    @Test
    public void testAddCategoryWhenFilmCategoriesIsNull() {
        // Inicializamos la película con la lista de categorías como null
        film.getFilmCategories().add(null);

        assertThrows(NullPointerException.class, () -> {
            film.addFilmCategory(category);
        }, "La lista de categorías no puede ser nula");
    }

}
