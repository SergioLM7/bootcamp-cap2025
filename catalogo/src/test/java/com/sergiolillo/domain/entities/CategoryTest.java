package com.sergiolillo.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CategoryTest {
	
    private Validator validator;
    
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, ACCIÓN",
        "2, MADROÑO",
        "3, CIENCIA FICCIÓN"
    })
	void testValidCreateCategory(int categoryId, String name) {
    	Category category = new Category(categoryId, name, new ArrayList<FilmCategory>());
    	
		assertNotNull(category);
		assertAll("Constructor de Category",
				() -> 		assertEquals(categoryId, category.getCategoryId()),
				() -> 		assertEquals(name, category.getName()),
				() ->		assertNotNull(category.getFilmCategories()),
				() -> 		assertNotNull(category.getLastUpdate())
				);
	}
    
    
    @ParameterizedTest
    @CsvSource({
        "0, ",
        "-1, JOHN",
    })
    public void testInvalidCategories(int categoryId, String name) {
    	Category category = new Category(categoryId, name, new ArrayList<FilmCategory>());
        
        assertTrue(categoryId <= 0 || name == null || name.isEmpty() || (name.length() > 45 && name.length() < 2) || category.getFilmCategories() == null);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"ESPAÑA", "ACCIÓN", "TERROR", "CIENCIA FICCIÓN"})
    public void testValidNames(String name) {
    	Category category = new Category(1, name, new ArrayList<FilmCategory>());
        
        assertEquals(name, category.getName());
        
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "españa", "12345", "Ciencia ficción"})
    public void testInvalidNames(String name) {
        Category category = new Category(1, name, new ArrayList<FilmCategory>());
        
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty());
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Category> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            expectedMessages.add("El nombre no puede estar vacío ni ser nulo");
            expectedMessages.add("El nombre debe estar en mayúsculas");
            expectedMessages.add("La longitud del nombre debe estar entre 2 y 45 caracteres");
        }
        if (!name.matches("^[A-ZÀ-Ö ]+$")) {
            expectedMessages.add("El nombre debe estar en mayúsculas");
        }
        if(name.length() < 2 || name.length() > 45)
        	expectedMessages.add("La longitud del nombre debe estar entre 2 y 45 caracteres");
        
        assertTrue(violationMessages.containsAll(expectedMessages), "Expected messages: " + expectedMessages + ", but got: " + violationMessages);
    }

}
