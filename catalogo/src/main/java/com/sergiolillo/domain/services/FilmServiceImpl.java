package com.sergiolillo.domain.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sergiolillo.domain.contracts.repositories.FilmRepository;
import com.sergiolillo.domain.contracts.services.FilmService;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FilmServiceImpl implements FilmService {
	
	FilmRepository repoFilm;
	
	public FilmServiceImpl(FilmRepository repoFilm) {
		this.repoFilm = repoFilm;
	}
	

	@Override
	public List<Film> getAll() {
		return repoFilm.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		Optional<Film> film = repoFilm.findById(id);

		return film;
	}

	@Override
	@Transactional
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		
		if(repoFilm.existsById(item.getFilmId()))
			throw new DuplicateKeyException(item.getErrorsMessage());
		
		return repoFilm.save(item);
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("La película no puede ser nula.");
		}
		
		if(!repoFilm.existsById(item.getFilmId())) {
			throw new NotFoundException("La película no existe.");
		}

		var leido = repoFilm.findById(item.getFilmId()).orElseThrow(() -> new NotFoundException());
		return repoFilm.save(item.merge(leido));
		
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		
		repoFilm.deleteById(item.getFilmId());		
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException, InvalidDataException {
		if(!repoFilm.findById(id).isPresent()) {
			throw new NotFoundException("La película no existe.");
		}
		
		repoFilm.deleteById(id);
		
	}


	@Override
	public List<Film> novedades(@NonNull Timestamp fecha) {
		return repoFilm.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
