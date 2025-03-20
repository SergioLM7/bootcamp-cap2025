package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sergiolillo.domain.contracts.repositories.LanguageRepository;
import com.sergiolillo.domain.contracts.services.LanguageService;
import com.sergiolillo.domain.entities.Language;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

@Service
public class LanguageServiceImpl implements LanguageService{
	
	LanguageRepository dao;
	
	public LanguageServiceImpl(LanguageRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Language> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Language> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Language add(Language item) throws DuplicateKeyException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("La categoría no puede ser nula");
		}
		
		if(item.getLanguageId() > 0 && dao.existsById(item.getLanguageId())) {
			throw new DuplicateKeyException("Este idioma ya está registrado.");
		}
		
		return dao.save(item);
	}

	@Override
	public Language modify(Language item) throws NotFoundException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("El idioma no puede ser nulo.");
		}
		
		if(!dao.existsById(item.getLanguageId())) {
			throw new NotFoundException("El idioma no existe.");
		}
		
		return dao.save(item);
	}

	@Override
	public void delete(Language item) throws InvalidDataException {
		if(!dao.findById(item.getLanguageId()).isPresent() || item == null) {
			throw new InvalidDataException("El idioma no existe.");
		}
	    
		dao.delete(item);
		
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException, InvalidDataException {
	    if (!dao.existsById(id)) {
	        throw new InvalidDataException("El idioma con el ID " + id + " no existe.");
	    }

	    dao.deleteById(id);
	}

	@Override
	public List<Language> findAllByOrderByName() {
		return dao.findAllByOrderByName();
	}

}
