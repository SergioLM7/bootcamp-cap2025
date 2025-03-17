package com.sergiolillo.domain.services;

import com.sergiolillo.domain.entities.Category;
import com.sergiolillo.domain.entities.FilmCategory;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.sergiolillo.domain.contracts.repositories.CategoryRepository;


class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repoCategory;

    @InjectMocks
    private CategoryServiceImpl categoryService; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    

    @Test
    void testGetAll() {
    	Category category1 = new Category(1, "CIENCIA FICCIÓN");
    	Category category2 = new Category(2, "DRAMA");
    	List<Category> categoriesMock = new ArrayList();
    	categoriesMock.add(category1);
    	categoriesMock.add(category2);
    	
        when(repoCategory.findAll()).thenReturn(categoriesMock);

        List<Category> categories = categoryService.getAll();

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(2, categories.size());
        assertEquals("CIENCIA FICCIÓN", categories.get(0).getName());

        verify(repoCategory, times(1)).findAll();
    }
    
    @Test
    void testGetAllEmpty() {
        when(repoCategory.findAll()).thenReturn(List.of());

        List<Category> result = categoryService.getAll();
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testGetOne() {
        Category category = new Category(1, "CIENCIA FICCIÓN");
        FilmCategory filmCategory = new FilmCategory();
        filmCategory.setCategory(category);
        category.setFilmCategories(List.of(filmCategory));
        
        when(repoCategory.findById(1)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getOne(1);

        assertTrue(result.isPresent());
        assertEquals("CIENCIA FICCIÓN", result.get().getName());
        assertFalse(result.get().getFilmCategories().isEmpty());

        verify(repoCategory, times(1)).findById(1);
    }
    
    @Test
    void testGetOneNotFound() {
        when(repoCategory.findById(1)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getOne(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testAddValidCategory() throws DuplicateKeyException, InvalidDataException {
        Category category = new Category(0, "CIENCIA FICCIÓN");
        when(repoCategory.save(category)).thenReturn(category);

        Category result = categoryService.add(category);
        assertEquals(category, result);
        
        verify(repoCategory, times(1)).save(category);
    }
    
    @Test
    void testAddDuplicateCategory() {
        Category category = new Category(1, "CIENCIA FICCIÓN");
        when(repoCategory.existsById(1)).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> categoryService.add(category));
        
        verify(repoCategory, times(0)).save(category);
    }

    @Test
    void testAddNullCategory() {
        assertThrows(InvalidDataException.class, () -> categoryService.add(null));
        
        verify(repoCategory, times(0)).save(null);
    }

    @Test
    void testModifyValidCategory() throws InvalidDataException, NotFoundException {
        Category category = new Category(1, "CIENCIA FICCIÓN");
        when(repoCategory.existsById(1)).thenReturn(true);
        when(repoCategory.save(category)).thenReturn(category);

        Category result = categoryService.modify(category);
        assertEquals(category, result);
        
        verify(repoCategory, times(1)).save(category);

    }
 

  
}
