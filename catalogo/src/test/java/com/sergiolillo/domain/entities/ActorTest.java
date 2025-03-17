package com.sergiolillo.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ActorTest {
	
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @CsvSource({
        "1, JUAN, GARCIA",
        "2, JANE, SMITH",
        "3, ALICE, JOHNSON"
    })
	void testValidCreateActor(int actorId, String firstName, String lastName) {
    	Actor actor = new Actor(actorId, firstName, lastName);
    	actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
    	
		assertNotNull(actor);
		assertAll("Constructor de Actor",
				() -> 		assertEquals(actorId, actor.getActorId()),
				() -> 		assertEquals(firstName, actor.getFirstName()),
				() ->		assertEquals(lastName, actor.getLastName()),
				() -> 		assertNotNull(actor.getLastUpdate())
				);
	}
    
    @ParameterizedTest
    @CsvSource({
        "0, , DOE",
        "1, JOHN, ",
        "2, , "
    })
    public void testInvalidActors(int actorId, String firstName, String lastName) {
        Actor actor = new Actor(actorId, firstName, lastName);
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        
        assertTrue(actorId <= 0 || firstName == null || firstName.isEmpty() || (firstName.length() > 45 && firstName.length() < 2) || lastName == null || lastName.isEmpty() ||  (lastName.length() > 45 && lastName.length() < 2));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"JOHN", "TOÑO", "ALICE", "MARÍA DEL MAR"})
    public void testValidFirstNames(String firstName) {
        Actor actor = new Actor(1, firstName, "DOE");
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        
        assertEquals(firstName, actor.getFirstName());
        
        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"john", " ", "caño", "12345", "María del Mar"})
    public void testInvalidFirstNames(String firstName) {
        Actor actor = new Actor(1, firstName, "DOE");
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        
        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty());
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Actor> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if (firstName == null || firstName.isEmpty()) {
            expectedMessages.add("El nombre no puede estar vacío ni ser nulo");
            expectedMessages.add("El nombre debe estar en mayúsculas");
            expectedMessages.add("La longitud del nombre debe estar entre 2 y 45 caracteres");
        }
        if (!firstName.matches("^[A-ZÀ-Ö ]+$")) {
            expectedMessages.add("El nombre debe estar en mayúsculas");
        }
        if(firstName.length() < 2 || firstName.length() > 45)
        	expectedMessages.add("La longitud del nombre debe estar entre 2 y 45 caracteres");
        
        assertTrue(violationMessages.containsAll(expectedMessages), "Expected messages: " + expectedMessages + ", but got: " + violationMessages);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"GARCÍA", "CASTAÑO", "DE TODOS LOS SANTOS"})
    public void testValidLastNames(String lastName) {
        Actor actor = new Actor(1, "JOHN", lastName);
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        
        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertTrue(violations.isEmpty());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"GARCÍa", " ", "caño", "12345", "DE TODOS LOS SANTOS1"})
    public void testInvalidLastName(String lastName) {
        Actor actor = new Actor(1, "JUAN", lastName);
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        
        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty());
        
        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<Actor> violation : violations) {
            violationMessages.add(violation.getMessage());
        }
        
        List<String> expectedMessages = new ArrayList<>();

        if (lastName == null || lastName.isEmpty()) {
            expectedMessages.add("El apellido no puede estar vacío ni ser nulo");
            expectedMessages.add("El apellido debe estar en mayúsculas");
            expectedMessages.add("La longitud del apellido debe estar entre 2 y 45 caracteres");
        }
        if (!lastName.matches("^[A-ZÀ-Ö ]+$")) {
            expectedMessages.add("El apellido debe estar en mayúsculas");
        }
        if(lastName.length() < 2 || lastName.length() > 45)
        	expectedMessages.add("La longitud del apellido debe estar entre 2 y 45 caracteres");
        
        assertTrue(violationMessages.containsAll(expectedMessages), "Expected messages: " + expectedMessages + ", but got: " + violationMessages);
    }
	

}
