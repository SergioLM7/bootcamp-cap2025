package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import com.sergiolillo.domain.contracts.repositories.FilmRepository;
import com.sergiolillo.domain.contracts.services.FilmService;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

public class FilmServiceImpl implements FilmService {
	
	FilmRepository repoFilm;
	
	public FilmServiceImpl(FilmRepository repoFilm) {
		this.repoFilm = repoFilm;
	}
	

	@Override
	public List<Film> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException, InvalidDataException {
		// TODO Auto-generated method stub
		
	}

}
