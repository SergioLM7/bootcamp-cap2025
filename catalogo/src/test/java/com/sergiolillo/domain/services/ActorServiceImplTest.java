package com.sergiolillo.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergiolillo.domain.contracts.repositories.ActoresRepository;
import com.sergiolillo.domain.entities.Actor;

public class ActorServiceImplTest {
	
	@Mock
	private ActoresRepository repoActor;
	
    @InjectMocks
    private ActoresServiceImpl actorService;
	
	private Actor actor;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
    @Test
    void testGetAll() {
        List<Actor> actors = Arrays.asList(new Actor(1, "JOHN", "DOE"), new Actor(2, "JANE", "SMITH"));
        when(repoActor.findAll()).thenReturn(actors);

        List<Actor> result = actorService.getAll();

        // Verifico que el resultado no es nulo ni vacío
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        
        //Verifico que los dos nombres son los esperados
        assertEquals("JOHN", result.get(0).getFirstName());
        assertEquals("JANE", result.get(1).getFirstName());

        // Verifico que el repositorio fue llamado una vez
        verify(repoActor).findAll();
    }
	

}
