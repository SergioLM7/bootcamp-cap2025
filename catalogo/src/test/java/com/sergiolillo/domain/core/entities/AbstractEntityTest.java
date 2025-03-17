package com.sergiolillo.domain.core.entities;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class AbstractEntityTest {
	
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Test
    void testIsValid_ValidEntity() {
        TestEntity entity = new TestEntity("Valid Name");

        Set<ConstraintViolation<TestEntity>> violations = validator.validate(entity);

        assertTrue(violations.isEmpty(), "No debería haber errores de validación");
    }
    
    @Test
    void testIsValid_InvalidEntity() {
        TestEntity entity = new TestEntity(null);

        Set<ConstraintViolation<TestEntity>> violations = validator.validate(entity);

        assertFalse(violations.isEmpty(), "Debería haber errores de validación");
    }
    
    @Test
    void testGetErrorsMessage_ValidEntity() {
        TestEntity entity = new TestEntity("Valid Name");

        String errorMessage = entity.getErrorsMessage();
        assertEquals("", errorMessage, "No debería haber errores para una entidad válida");
    }
    
    @Test
    void testGetErrorsMessage_InvalidEntity() {
        TestEntity entity = new TestEntity(null); 

        String errorMessage = entity.getErrorsMessage();
        assertTrue(errorMessage.contains("ERRORES"), "El mensaje de errores debería contener la violación de restricción");
    }
    
    @Test
    void testGetErrorsFields_WithoutErrors() {
        TestEntity entity = new TestEntity("Valid Name");

        Map<String, String> errorsFields = entity.getErrorsFields();

        assertNull(errorsFields, "Cuando no hay errores, getErrorsFields() debería devolver null");
    }
    
    @Test
    void testGetErrorsFields_WithErrors() {
        TestEntity entity = new TestEntity(null); 

        Map<String, String> errorsFields = entity.getErrorsFields();

        assertNotNull(errorsFields, "Cuando hay errores, getErrorsFields() no debería devolver null");
        assertTrue(errorsFields.containsKey("name"), "El mapa de errores debería contener el campo 'name'");
        assertTrue(errorsFields.get("name").contains("null"), "El error para el campo 'name' debería contener el mensaje 'may not be null'");
    }



    @Test
    void testIsInvalid_InvalidEntity() {
        TestEntity entity = new TestEntity(null);
        assertTrue(entity.isInvalid(), "La entidad debería ser inválida");
    }
    
    @Test
    void testIsValid_Entity() {
    	 TestEntity entity = new TestEntity(null); 
         assertFalse(entity.isValid(), "La entidad debería ser inválida debido a que 'name' es null");
     
    }

}
