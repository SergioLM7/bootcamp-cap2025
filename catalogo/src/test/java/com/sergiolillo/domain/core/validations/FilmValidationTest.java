package com.sergiolillo.domain.core.validations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.Language;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilmValidationTest {
    
    private Validator validator;
    private Film film;
    private Language language;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        language = new Language();
        language.setName("English");
        
        film = new Film(1, "Test Film", 120, "PG", (short) 2025, (byte) 7, new BigDecimal("4.99"), new BigDecimal("19.99"), "Test Title", language);
    }
    
    @Test
    public void testValidFilm() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "No debe haber violaciones de validación para el film válido");
    }

    @Test
    public void testInvalidTitleShort() {
        film.setTitle("T"); 

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación para el título corto");
        assertEquals(1, violations.size(), "Debe haber una violación para el título corto");
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Film> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if(film.getTitle().length() < 2 || film.getTitle().length() > 128)
        	expectedMessages.add("La longitud del título debe estar entre 2 y 128 caracteres");
        
        if(film.getTitle().isBlank())
        	expectedMessages.add("El title no puede ser nulo ni estar vacío");
        
        for (String expectedMessage : expectedMessages) {
            assertTrue(violationMessages.contains(expectedMessage),
                    "Expected message: " + expectedMessage + ", but got: " + violationMessages);
        }	
    }
    
    @Test
    public void testInvalidTitleEmpty() {
        film.setTitle("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación para el título vacío");
        assertEquals(2, violations.size(), "Debe haber una violación para el título vacío");
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Film> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if(film.getTitle().length() < 2 || film.getTitle().length() > 128)
        	expectedMessages.add("La longitud del título debe estar entre 2 y 128 caracteres");
        
        if(film.getTitle().isBlank())
        	expectedMessages.add("El title no puede ser nulo ni estar vacío");
        
        for (String expectedMessage : expectedMessages) {
            assertTrue(violationMessages.contains(expectedMessage),
                    "Expected message: " + expectedMessage + ", but got: " + violationMessages);
        }	
    }
    
    @Test
    public void testInvalidRentalRateNull() {
        film.setRentalRate(null); 

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación para el rentalRate negativo");
        assertEquals(1, violations.size(), "Debe haber una violación para el rentalRate negativo");
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Film> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if(film.getRentalRate() == null)
        	expectedMessages.add("El rentalRate no puede ser nulo");
        
        for (String expectedMessage : expectedMessages) {
            assertTrue(violationMessages.contains(expectedMessage),
                    "Expected message: " + expectedMessage + ", but got: " + violationMessages);
        }	
    }
    
    @Test
    public void testInvalidRentalRateNegative() {
        film.setRentalRate(new BigDecimal("-5.99")); 

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación para el rentalRate negativo");
        assertEquals(1, violations.size(), "Debe haber una violación para el rentalRate negativo");
        	
    }
    
    @Test
    public void testInvalidLengthNegative() {
        film.setLength(-1);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación para la longitud negativa");
        assertEquals(1, violations.size(), "Debe haber una violación para la longitud negativa");
    }

    @Test
    public void testInvalidLengthZero() {
        film.setLength(0); 

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación para la longitud cero");
        assertEquals(1, violations.size(), "Debe haber una violación para la longitud cero");
    }

    @Test
    public void testInvalidRatingNull() {
        film.setRating(null); 

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "No debe haber violaciones para el rating nulo");
    }
    


}
