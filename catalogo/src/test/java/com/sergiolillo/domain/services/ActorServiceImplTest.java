package com.sergiolillo.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergiolillo.domain.contracts.repositories.ActoresRepository;
import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

public class ActorServiceImplTest {
	
	@Mock
	private ActoresRepository repoActor;
	
    @InjectMocks
    private ActoresServiceImpl actorService;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
    @Test
    void testGetAll() {
        List<Actor> actors = Arrays.asList(new Actor(1, "JOHN", "DOE"), new Actor(2, "JANE", "SMITH"));
        when(repoActor.findAll()).thenReturn(actors);

        List<Actor> result = actorService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(actors.size(), result.size());
        
        assertEquals(actors.getFirst().getFirstName(), result.get(0).getFirstName());
        assertEquals(actors.getLast().getFirstName(), result.get(1).getFirstName());

        verify(repoActor, times(1)).findAll();
    }
    
    
    @Test
    public void testGetAllEmpty() {
        when(repoActor.findAll()).thenReturn(List.of());

        List<Actor> result = actorService.getAll();
        assertTrue(result.isEmpty());
        
        verify(repoActor, times(1)).findAll();
    }

    @Test
    public void testGetAllException() {
        when(repoActor.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> actorService.getAll());
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testGetOne() {
        Actor actor = new Actor(1, "JOHN", "DOE");
        when(repoActor.findById(1)).thenReturn(Optional.of(actor));

        Optional<Actor> result = actorService.getOne(1);
        assertTrue(result.isPresent());
        assertEquals(actor, result.get());
        
        verify(repoActor, times(1)).findById(1);
    }

    @Test
    public void testAddValidActor() throws DuplicateKeyException, InvalidDataException {
        Actor actor = new Actor(0, "JOHN", "DOE");
        when(repoActor.save(actor)).thenReturn(actor);

        Actor result = actorService.add(actor);
        assertEquals(actor, result);
        
        verify(repoActor, times(1)).save(actor);

    }

    @Test
    public void testAddDuplicateActor() {
        Actor actor = new Actor(1, "JOHN", "DOE");
        when(repoActor.existsById(1)).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> actorService.add(actor));
        
        verify(repoActor, times(0)).save(actor);
    }

    @Test
    public void testModifyValidActor() throws NotFoundException, InvalidDataException {
        Actor actor = new Actor(1, "JOHN", "DOE");
        when(repoActor.existsById(1)).thenReturn(true);
        when(repoActor.save(actor)).thenReturn(actor);

        Actor result = actorService.modify(actor);
        assertEquals(actor, result);
        
        verify(repoActor, times(1)).save(actor);
    }

    @Test
    public void testModifyNonExistentActor() {
        Actor actor = new Actor(1, "JOHN", "DOE");
        when(repoActor.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> actorService.modify(actor));
        
        verify(repoActor, times(0)).save(actor);
    }

    @Test
    public void testDeleteValidActor() throws InvalidDataException {
        Actor actor = new Actor(1, "JOHN", "DOE");
        when(repoActor.findById(1)).thenReturn(Optional.of(actor));

        actorService.delete(actor);
        verify(repoActor).delete(actor);
    }

    @Test
    public void testDeleteNonExistentActor() {
        Actor actor = new Actor(1, "JOHN", "DOE");
        when(repoActor.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidDataException.class, () -> actorService.delete(actor));
        
        verify(repoActor, times(0)).delete(actor);
    }

    @Test
    public void testDeleteByIdValid() throws NotFoundException {
        when(repoActor.findById(1)).thenReturn(Optional.of(new Actor(1, "JOHN", "DOE")));

        actorService.deleteById(1);
        verify(repoActor).deleteById(1);
    }

    @Test
    public void testDeleteByIdNonExistent() {
        when(repoActor.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> actorService.deleteById(1));
        
        verify(repoActor, times(0)).deleteById(1);
    }
	

}
