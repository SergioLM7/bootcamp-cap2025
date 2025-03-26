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

public class LanguageTest {
    private Validator validator;
    
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, Inglés",
        "2, Español",
        "3, Swahili"
    })
	void testValidCreateLanguage(int languageId, String name) {
    	Language language = new Language(languageId, name);
    	
		assertNotNull(language);
		assertAll("Constructor de Language",
				() -> 		assertEquals(languageId, language.getLanguageId()),
				() -> 		assertEquals(name, language.getName()),
				() -> 		assertNotNull(language.getLastUpdate())
				);
	}
    
    
    @ParameterizedTest
    @CsvSource({
        "0, INGLÉS",
        "-1, Italiano",
        "2, ",
        "3, S",
        "2, INGLÉSINGLÉSINGLÉSINGLÉS",
    })
    public void testInvalidLanguages(int languageId, String name) {
    	Language language = new Language(languageId, name);
        
        assertTrue(languageId <= 0 || name == null || name.isEmpty() || name.length() > 20 || name.length() < 2);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"Inglés", "Español", "Japonés", "Coreano"})
    public void testValidNames(String name) {
    	Language language = new Language(2, name);
        
        assertEquals(name, language.getName());
        
        Set<ConstraintViolation<Language>> violations = validator.validate(language);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "inglés", "12345", "COREANO"})
    public void testInvalidNames(String name) {
    	Language language = new Language(2, name);
        
        Set<ConstraintViolation<Language>> violations = validator.validate(language);
        assertFalse(violations.isEmpty());
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Language> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            expectedMessages.add("El nombre no puede estar vacío ni ser nulo");
            expectedMessages.add("El nombre debe tener una primera letra mayúscula y el resto, minúsculas. No acepta números");
            expectedMessages.add("La longitud del nombre debe estar entre 2 y 20 caracteres");
        }
        if (!name.matches("^[A-ZÀ-Ö][a-zà-ö -]+$")) {
            expectedMessages.add("El nombre debe tener una primera letra mayúscula y el resto, minúsculas. No acepta números");
        }
        if(name.length() < 2 || name.length() > 20)
        	expectedMessages.add("La longitud del nombre debe estar entre 2 y 20 caracteres");
        
        assertTrue(violationMessages.containsAll(expectedMessages), "Expected messages: " + expectedMessages + ", but got: " + violationMessages);
    }
}
