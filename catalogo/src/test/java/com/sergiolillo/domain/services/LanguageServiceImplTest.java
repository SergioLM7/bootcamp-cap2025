package com.sergiolillo.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergiolillo.domain.contracts.repositories.LanguageRepository;
import com.sergiolillo.domain.entities.Language;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

public class LanguageServiceImplTest {
    @Mock
    private LanguageRepository repoLanguage;

    @InjectMocks
    private LanguageServiceImpl languageService; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    

    @Test
    void testGetAll() {
    	Language language1 = new Language(1, "INGLÉS");
    	Language category2 = new Language(2, "ESPAÑOL");
    	List<Language> categoriesMock = new ArrayList();
    	categoriesMock.add(language1);
    	categoriesMock.add(category2);
    	
        when(repoLanguage.findAll()).thenReturn(categoriesMock);

        List<Language> languages = languageService.getAll();

        assertNotNull(languages);
        assertFalse(languages.isEmpty());
        assertEquals(2, languages.size());
        assertEquals("INGLÉS", languages.get(0).getName());

        verify(repoLanguage, times(1)).findAll();
    }
    
    @Test
    void testGetAllEmpty() {
        when(repoLanguage.findAll()).thenReturn(List.of());

        List<Language> result = languageService.getAll();
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testGetOne() {
        Language language = new Language(1, "INGLÉS");
        
        when(repoLanguage.findById(1)).thenReturn(Optional.of(language));

        Optional<Language> result = languageService.getOne(1);

        assertTrue(result.isPresent());
        assertEquals("INGLÉS", result.get().getName());

        verify(repoLanguage, times(1)).findById(1);
    }
    
    @Test
    void testGetOneNotFound() {
        when(repoLanguage.findById(1)).thenReturn(Optional.empty());

        Optional<Language> result = languageService.getOne(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testAddValidCategory() throws DuplicateKeyException, InvalidDataException {
    	Language language = new Language(0, "ESPAÑOL");
        when(repoLanguage.save(language)).thenReturn(language);

        Language result = languageService.add(language);
        assertEquals(language, result);
        
        verify(repoLanguage, times(1)).save(language);
    }
    
    @Test
    void testAddDuplicateCategory() {
    	Language language = new Language(1, "ESPAÑOL");
        when(repoLanguage.existsById(1)).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> languageService.add(language));
        
        verify(repoLanguage, times(0)).save(language);
    }

    @Test
    void testAddNullCategory() {
        assertThrows(InvalidDataException.class, () -> languageService.add(null));
        
        verify(repoLanguage, times(0)).save(null);
    }

    @Test
    void testModifyValidCategory() throws InvalidDataException, NotFoundException {
        Language language = new Language(1, "JAPONÉS");
        when(repoLanguage.existsById(1)).thenReturn(true);
        when(repoLanguage.save(language)).thenReturn(language);

        Language result = languageService.modify(language);
        assertEquals(language, result);
        
        verify(repoLanguage, times(1)).save(language);

    }
 

}
