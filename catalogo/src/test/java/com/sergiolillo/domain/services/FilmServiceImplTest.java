package com.sergiolillo.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergiolillo.domain.contracts.repositories.FilmRepository;
import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.FilmActor;
import com.sergiolillo.domain.entities.Language;
import com.sergiolillo.domain.entities.Film.Rating;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;



public class FilmServiceImplTest {
	
	@Mock
	private FilmRepository repoFilm;
	
    @InjectMocks
    private FilmServiceImpl filmService;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    

    
    @Test
    public void testGetAll() {
        Language language = new Language();
        Film film1 = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));
        Film film2 = new Film(2, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));
        List<Film> films = Arrays.asList(film1, film2);
        
        when(repoFilm.findAll()).thenReturn(films);
        
        List<Film> result = filmService.getAll();
        assertEquals(films, result);
        verify(repoFilm, times(1)).findAll();
    }
    
    @Test
    public void testAddValidFilm() throws DuplicateKeyException, InvalidDataException {
        Language language = new Language();
        Film film = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));
        when(repoFilm.save(film)).thenReturn(film);
        
        Film result = filmService.add(film);
        assertEquals(film, result);

        verify(repoFilm, times(1)).save(film);
    }


    @Test
    public void testAddDuplicateFilm() throws DuplicateKeyException, InvalidDataException {
        Language language = new Language();
        Film film = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));
        when(repoFilm.existsById(1)).thenReturn(true);
        
        assertThrows(DuplicateKeyException.class, () -> filmService.add(film));
        
        verify(repoFilm, times(0)).save(film);
    }
    
    @Test
    public void testModify() throws NotFoundException, InvalidDataException {
        Language language = new Language();
        Film existingFilm = new Film(1, "Existing Title", "Existing description", (short) 2020, language, null, (byte) 5, new BigDecimal("3.99"), 150, new BigDecimal("15.99"), Rating.getEnum("PG"));
        Film newFilm = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));

        when(repoFilm.existsById(newFilm.getFilmId())).thenReturn(true);
        when(repoFilm.findById(newFilm.getFilmId())).thenReturn(Optional.of(existingFilm));
        when(repoFilm.save(any(Film.class))).thenReturn(newFilm);

        Film result = filmService.modify(newFilm);
        assertEquals(newFilm.getTitle(), result.getTitle());
        assertEquals(newFilm.getDescription(), result.getDescription());
        assertEquals(newFilm.getReleaseYear(), result.getReleaseYear());
        assertEquals(newFilm.getLanguage(), result.getLanguage());
        assertEquals(newFilm.getRentalDuration(), result.getRentalDuration());
        assertEquals(newFilm.getRentalRate(), result.getRentalRate());
        assertEquals(newFilm.getLength(), result.getLength());
        assertEquals(newFilm.getReplacementCost(), result.getReplacementCost());
        assertEquals(newFilm.getRating(), result.getRating());

        verify(repoFilm, times(1)).existsById(newFilm.getFilmId());
        verify(repoFilm, times(1)).findById(newFilm.getFilmId());
        verify(repoFilm, times(1)).save(any(Film.class));
    }

    @Test
    public void testModifyNotFoundException() throws NotFoundException, InvalidDataException {
        Language language = new Language();
        Film film = new Film(1, "Test Title", "A sample description", (short) 2025, language, null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));
        when(repoFilm.existsById(film.getFilmId())).thenReturn(false);
        
        assertThrows(NotFoundException.class, () -> filmService.modify(film));
        
        verify(repoFilm, times(0)).save(film);
    }
    
    public void testNovedades() {
        Timestamp fecha = Timestamp.valueOf("2025-01-01 00:00:00");
        
        Film film1 = new Film(
                1, 
                "Film 1", 
                new Language(1, "English"), 
                (byte) 3, 
                new BigDecimal("2.99"), 
                new BigDecimal("19.99")
            );

            Film film2 = new Film(
                2, 
                "Film 2", 
                new Language(2, "Spanish"), 
                (byte) 4, 
                new BigDecimal("3.99"), 
                new BigDecimal("24.99")
            );
            
        List<Film> films = Arrays.asList(film1, film2);

        when(repoFilm.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha)).thenReturn(films);

        List<Film> resultado = filmService.novedades(fecha);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Film 1", resultado.get(0).getTitle());
        assertEquals("Film 2", resultado.get(1).getTitle());

        verify(repoFilm, times(1)).findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
    }
    
    @Test
    public void testNovedadesNoResults() {
        Timestamp fecha = Timestamp.valueOf("2025-01-01 00:00:00");

        when(repoFilm.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha)).thenReturn(Arrays.asList());

        List<Film> resultado = filmService.novedades(fecha);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "La lista debe estar vacía si no se encuentran resultados");
    }
    
    @Test
    public void testDelete() throws InvalidDataException {
        Film film = new Film(1, "Test Title", "A sample description", (short) 2025, new Language(), null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));

        doNothing().when(repoFilm).deleteById(film.getFilmId());

        filmService.delete(film);

        verify(repoFilm, times(1)).deleteById(film.getFilmId());
    }
    
    @Test
    public void testDeleteById() throws NotFoundException, InvalidDataException {
        Integer filmId = 1;

        when(repoFilm.findById(filmId)).thenReturn(Optional.of(new Film()));

        doNothing().when(repoFilm).deleteById(filmId);

        filmService.deleteById(filmId);

        verify(repoFilm, times(1)).findById(filmId);
        verify(repoFilm, times(1)).deleteById(filmId);
    }
    
    @Test
    public void testGetByProjection() {
        Class<FilmDetailsDTO> projectionType = FilmDetailsDTO.class;
        Film film = new Film(1, "Test Title", "A sample description", (short) 2025, new Language(), null, (byte) 7, new BigDecimal("4.99"), 180, new BigDecimal("19.99"), Rating.getEnum("PG"));
        FilmDetailsDTO expectedFilmDetails = FilmDetailsDTO.from(film);
        List<FilmDetailsDTO> expectedFilms = List.of(expectedFilmDetails);

        when(repoFilm.findAllBy(projectionType)).thenReturn(expectedFilms);

        List<FilmDetailsDTO> result = filmService.getByProjection(projectionType);

        assertEquals(expectedFilms, result);
        verify(repoFilm, times(1)).findAllBy(projectionType);
    }


}
