package com.sergiolillo.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sergiolillo.domain.entities.Film.Rating;


public class FilmTest {
	
;
    private FilmActor actor;
    private Language language;
    private Film film = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));

    private FilmCategory category;

    @Test
    public void testConstructorFullParams() {
        Film film = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));

        assertNotNull(film);
        
        assertAll("Comprobaciones de Constructor Completo",
            () -> assertEquals("Test Title", film.getTitle()),
            () -> assertEquals("A sample description", film.getDescription()),
            () -> assertEquals((short) 2025, film.getReleaseYear()),
            () -> assertEquals(language, film.getLanguage()),
            () -> assertNull(film.getLanguageVO()),
            () -> assertEquals(7, film.getRentalDuration()),
            () -> assertEquals(new BigDecimal("4.99"), film.getRentalRate()),
            () -> assertEquals(new BigDecimal("19.99"), film.getReplacementCost()),
            () -> assertEquals(Rating.PARENTAL_GUIDANCE_SUGGESTED, film.getRating())
        );
    }

    @Test
    public void testConstructorBasicParams() {
        Film film = new Film(1, "Test Title", language, (byte) 7, new BigDecimal("4.99"), new BigDecimal("19.99"));

        assertNotNull(film);

        assertAll("Comprobaciones de Constructor Básico",
            () -> assertEquals("Test Title", film.getTitle()),
            () -> assertNull(film.getDescription()),
            () -> assertNull(film.getLanguageVO()),
            () -> assertEquals(7, film.getRentalDuration()),
            () -> assertEquals(new BigDecimal("4.99"), film.getRentalRate()),
            () -> assertEquals(new BigDecimal("19.99"), film.getReplacementCost())
        );
    }
    
    @Test
    public void testConstructorWithFilmId() {
        Film film = new Film(1);

        assertNotNull(film);
        
        assertAll("Comprobaciones de Constructor con solo filmId",
            () -> assertEquals(1, film.getFilmId()),
            () -> assertNull(film.getTitle()),
            () -> assertNull(film.getDescription()),
            () -> assertNull(film.getReleaseYear()),
            () -> assertNull(film.getLanguage()),
            () -> assertNull(film.getLanguageVO()),
            () -> assertEquals(0, film.getRentalDuration()),
            () -> assertNull(film.getRentalRate()),
            () -> assertNull(film.getReplacementCost()),
            () -> assertNull(film.getRating())
        );
    }

   

    @Test
    public void testAddActor() {
        Actor actor = new Actor(1, "Johnny", "Depp");
        film.addActor(actor);

        assertAll("Comprobaciones después de añadir un actor",
            () -> assertEquals(1, film.getActors().size(), "Debe haber un actor en la lista de actores"),
            () -> assertTrue(film.getActors().stream().anyMatch(fa -> fa.toString().equals(actor.toString())), "El actor debe estar en la lista")
        );
    }

    @Test
    public void testRemoveActorByActor() {
        Actor actor = new Actor(1, "Johnny", "Depp");
        film.addActor(actor);

        film.removeActor(actor);

        assertAll("Comprobaciones después de eliminar un actor por objeto",
            () -> assertEquals(0, film.getActors().size(), "No debe haber actores en la lista"),
            () -> assertFalse(film.getActors().stream().anyMatch(fa -> fa.toString().equals(actor.toString())), "El actor ya no debe estar en la lista")
        );
    }
    
    @Test
    public void testRemoveActorByActorId() {
        Actor actor = new Actor(1, "Johnny", "Depp");
        film.addActor(actor);

        film.removeActor(actor.getActorId());

        assertAll("Comprobaciones después de eliminar un actor por ID",
            () -> assertEquals(0, film.getActors().size(), "No debe haber actores en la lista"),
            () -> assertFalse(film.getActors().stream().anyMatch(fa -> fa.toString().equals(actor.toString())), "El actor ya no debe estar en la lista")
        );
    }
    
    @Test
    public void testGetCategories() {
        Category category = new Category(1);
        film.addCategory(category);

        List<Category> categories = film.getCategories();

        assertAll("Test de obtener categorías",
                () -> assertNotNull(categories, "La lista de categorías no debe ser nula"),
                () -> assertEquals(1, categories.size(), "Debe haber una categoría"),
                () -> assertTrue(categories.contains(category), "La categoría debe estar en la lista")
            );
    }
    
    @Test
    public void testSetCategories() {
        Category category1 = new Category(1);
        Category category2 = new Category(2);
        List<Category> newCategories = new ArrayList<>();
        newCategories.add(category1);
        newCategories.add(category2);

        film.setCategories(newCategories);

        List<Category> categories = film.getCategories();
        assertAll("Test de setear categorías",
                () -> assertNotNull(film.getCategories(), "La lista de categorías no debe ser nula"),
                () -> assertEquals(2, film.getCategories().size(), "Debe haber dos categorías"),
                () -> assertTrue(film.getCategories().contains(category1), "La categoría 1 debe estar en la lista"),
                () -> assertTrue(film.getCategories().contains(category2), "La categoría 2 debe estar en la lista")
            );
    }
    
    @Test
    public void testClearCategories() {
        Category category = new Category(1);
        film.addCategory(category);

        film.clearCategories();

        assertAll("Test de vaciar categorías",
            () -> assertNotNull(film.getCategories(), "La lista de categorías no debe ser nula"),
            () -> assertTrue(film.getCategories().isEmpty(), "La lista de categorías debe estar vacía")
        );
    }
    
    @Test
    public void testAddCategory() {
        Category category = new Category(1);

        film.addCategory(category);

        assertAll("Test de añadir categoría",
            () -> assertNotNull(film.getCategories(), "La lista de categorías no debe ser nula"),
            () -> assertEquals(1, film.getCategories().size(), "Debe haber una categoría"),
            () -> assertTrue(film.getCategories().contains(category), "La categoría debe estar en la lista")
        );
    }
    
    @Test
    public void testAddCategoryById() {
        film.addCategory(2); 

        assertAll("Test de añadir categoría por ID",
            () -> assertNotNull(film.getCategories(), "La lista de categorías no debe ser nula"),
            () -> assertEquals(1, film.getCategories().size(), "Debe haber una categoría"),
            () -> assertTrue(film.getCategories().stream().anyMatch(c -> c.getCategoryId() == 2), "Debe contener la categoría con ID 2")
        );
    }
    
    @Test
    public void testRemoveCategory() {
        Category category = new Category(1);
        film.addCategory(category);

        film.removeCategory(category);

        assertAll("Test de eliminar una categoría existente",
            () -> assertNotNull(film.getCategories(), "La lista de categorías no debe ser nula"),
            () -> assertFalse(film.getCategories().contains(category), "La categoría no debe estar en la lista")
        );
    }
    
    @Test
    public void testRemoveCategoryById() {
        Category category = new Category(1);
        film.addCategory(category);

        film.removeCategory(1);

        assertAll("Test de eliminar una categoría por ID",
            () -> assertNotNull(film.getCategories(), "La lista de categorías no debe ser nula"),
            () -> assertFalse(film.getCategories().contains(category), "La categoría no debe estar en la lista")
        );
    }
    
}
