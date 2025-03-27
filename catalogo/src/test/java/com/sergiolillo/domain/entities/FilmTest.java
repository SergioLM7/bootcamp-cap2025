package com.sergiolillo.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sergiolillo.domain.entities.Film.Rating;


public class FilmTest {
	
    private Actor actor = new Actor(1, "Johnny", "Depp");

    private Language language;
    private Film film = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));

    private FilmCategory category;

    @Test
    public void testConstructorFullParams() {
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
        film = new Film(1, "Test Title", language, (byte) 7, new BigDecimal("4.99"), new BigDecimal("19.99"));

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
        film = new Film(1);

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
        film.addActor(actor);

        assertAll("Comprobaciones después de añadir un actor",
            () -> assertEquals(1, film.getActors().size(), "Debe haber un actor en la lista de actores"),
            () -> assertTrue(film.getActors().stream().anyMatch(fa -> fa.toString().equals(actor.toString())), "El actor debe estar en la lista")
        );
    }

    @Test
    public void testRemoveActorByActor() {
        film.addActor(actor);

        film.removeActor(actor);

        assertAll("Comprobaciones después de eliminar un actor por objeto",
            () -> assertEquals(0, film.getActors().size(), "No debe haber actores en la lista"),
            () -> assertFalse(film.getActors().stream().anyMatch(fa -> fa.toString().equals(actor.toString())), "El actor ya no debe estar en la lista")
        );
    }
    
    @Test
    public void testRemoveActorByActorId() {
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
    
    @Test
    public void testAddActorToFilm() {
        film.addActor(actor);

        assertAll("Verificaciones después de añadir Actor a Film",
            () -> assertNotNull(film.getActors(), "La lista de actores no debe ser nula"),
            () -> assertEquals(1, film.getActors().size(), "Debe haber un actor asociado a la película"),
            () -> assertTrue(film.getActors().stream().anyMatch(fa -> fa.getActorId() == (actor.getActorId())), "El actor debe estar en la lista")
        );
    }
    
    @Test
    public void testRemoveActorFromFilm() {
        film.addActor(actor);

        film.removeActor(actor);

        assertAll("Verificaciones después de eliminar actor",
            () -> assertTrue(film.getActors().isEmpty(), "La lista de actores debe estar vacía"),
            () -> assertFalse(film.getActors().stream().anyMatch(fa -> fa.getActorId() == (actor.getActorId())), "El actor no debe estar en la lista")
        );
    }
    
    @Test
    public void testFilmActorPKRelationship() {
        FilmActor filmActor = new FilmActor(film, actor);
        
        filmActor.prePersiste();

        FilmActorPK id = filmActor.getId();
        assertAll("Verificación de la clave primaria compuesta",
        	() -> assertNotNull(id, "El id de FilmActor no debe ser nulo"),
            () -> assertEquals(1, id.getFilmId(), "El filmId debe ser 1"),
            () -> assertEquals(1, id.getActorId(), "El actorId debe ser 1")
        );
    }
    
    @Test
    public void testFilmActorPersistence() {
        FilmActor filmActor = new FilmActor(film, actor);

        assertAll("Verificación de FilmActor y sus entidades asociadas",
            () -> assertNotNull(filmActor.getActor(), "El actor no debe ser nulo"),
            () -> assertNotNull(filmActor.getFilm(), "La película no debe ser nula"),
            () -> assertEquals(film, filmActor.getFilm(), "La película debe ser la misma"),
            () -> assertEquals(actor, filmActor.getActor(), "El actor debe ser el mismo")
        );
    }
    
    @Test
    public void testMergeActorsAndCategories() {
        Actor actor1 = new Actor();
        actor1.setActorId(1);
        Actor actor2 = new Actor();
        actor2.setActorId(2);
        
        film.addActor(actor1);
        film.addActor(actor2);
        
        Category category1 = new Category();
        category1.setCategoryId(1);
        film.addCategory(category1);

        Film target = new Film(1, "Old Title", "Old Description", (short) 2020, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("G"));

        film.merge(target);

       assertAll("Verificaciones después de merge",
            () -> assertTrue(target.getActors().contains(actor1), "El actor 1 debe estar en el target"),
            () -> assertTrue(target.getActors().contains(actor2), "El actor 2 debe estar en el target"),
            () -> assertTrue(target.getCategories().contains(category1), "La categoría 1 debe estar en el target")
        );
    }

}
