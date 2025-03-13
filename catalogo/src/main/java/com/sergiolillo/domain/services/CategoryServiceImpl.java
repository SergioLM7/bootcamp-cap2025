package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly=true)
	public List<Category> getAll() {
		List<Category> categories = dao.findAll();
        categories.forEach(category -> Hibernate.initialize(category.getFilmCategories()));

		return categories;
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Category> getOne(Integer id) {
		Optional<Category> category = dao.findById(id);
        Hibernate.initialize(category.get().getFilmCategories());

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Category item) throws InvalidDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
