package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergiolillo.domain.contracts.repositories.ActoresRepository;
import com.sergiolillo.domain.contracts.repositories.CategoryRepository;
import com.sergiolillo.domain.contracts.services.CategoryService;
import com.sergiolillo.domain.entities.Category;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	CategoryRepository dao;
	
	public CategoryServiceImpl(CategoryRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Category> getAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Category> getOne(Integer id) {
		Optional<Category> category = dao.findById(id);
        category.ifPresent(c -> c.getFilmCategories().size());

		return category;
	}

	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("La categoría no puede ser nula");
		}
		
		if(item.getCategoryId() > 0 && dao.existsById(item.getCategoryId())) {
			throw new DuplicateKeyException("Esta categoría ya está registrada.");
		}
		
		return dao.save(item);
	}

	@Override
	public Category modify(Category item) throws NotFoundException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("La categoría no puede ser nulo.");
		}
		
		if(!dao.existsById(item.getCategoryId())) {
			throw new NotFoundException("La categoría no existe.");
		}
		
		return dao.save(item);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) throws InvalidDataException {
	    if (!dao.existsById(id)) {
	        throw new InvalidDataException("La categoría con el ID " + id + " no existe.");
	    }

	    Category category = dao.findById(id).orElseThrow(() -> new InvalidDataException("La categoría no existe"));

	    category.getFilmCategories().forEach(filmCategory -> {
	        filmCategory.setCategory(null);
	    });

	    dao.deleteById(id);
	}

	@Override
	public void delete(Category item) throws InvalidDataException {
		if(!dao.findById(item.getCategoryId()).isPresent() || item == null) {
			throw new InvalidDataException("La categoría no existe.");
		}
		
	    Category category = dao.findById(item.getCategoryId()).orElseThrow(() -> new InvalidDataException("La categoría no existe"));

	    category.getFilmCategories().forEach(filmCategory -> {
	        filmCategory.setCategory(null);
	    });
	    
		dao.delete(item);
	}

}
