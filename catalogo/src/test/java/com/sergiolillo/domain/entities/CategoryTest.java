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
        "1, Acción",
        "2, Madroño",
        "3, Ciencia Ficción"
    })
	void testValidCreateCategory(int categoryId, String name) {
    	Category category = new Category(categoryId, name);
    	
		assertNotNull(category);
		assertAll("Constructor de Category",
				() -> 		assertEquals(categoryId, category.getCategoryId()),
				() -> 		assertEquals(name, category.getName()),
				() -> 		assertNotNull(category.getLastUpdate())
				);
	}
    
    
    @ParameterizedTest
    @CsvSource({
        "1, ",
        "-1, Romántica",
        "1, S",
        "10, ROMÁNTICASROMÁNTICASROMÁNTICASROMÁNTICASROMÁNTICAS",

    })
    public void testInvalidCategories(int categoryId, String name) {
    	Category category = new Category(categoryId, name);
        
        assertTrue(categoryId <= 0 || name == null || name.isEmpty() || name.length() > 45 || name.length() < 2);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"España", "Acción", "Terror", "Ciencia ficción"})
    public void testValidNames(String name) {
    	Category category = new Category(1, name);
        
        assertEquals(name, category.getName());
        
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "ESPAÑA", "12345", "CIENCIA FICCIÓN"})
    public void testInvalidNames(String name) {
        Category category = new Category(1, name);
        
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty());
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Category> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            expectedMessages.add("El nombre no puede estar vacío ni ser nulo");
            expectedMessages.add("El nombre debe tener una primera letra mayúscula y el resto, minúsculas. No acepta números");
            expectedMessages.add("La longitud del nombre debe estar entre 2 y 45 caracteres");
        }
        if (!name.matches("^[A-ZÀ-Ö][a-zà-ö -]+$")) {
            expectedMessages.add("El nombre debe tener una primera letra mayúscula y el resto, minúsculas. No acepta números");
        }
        if(name.length() < 2 || name.length() > 45)
        	expectedMessages.add("La longitud del nombre debe estar entre 2 y 45 caracteres");
        
        assertTrue(violationMessages.containsAll(expectedMessages), "Expected messages: " + expectedMessages + ", but got: " + violationMessages);
    }

}
