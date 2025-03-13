package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
	public Optional<Category> getOne(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
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
