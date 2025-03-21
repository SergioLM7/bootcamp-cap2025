package com.sergiolillo.domain.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.sergiolillo.domain.contracts.repositories.FilmRepository;
import com.sergiolillo.domain.contracts.services.FilmService;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;
import com.sergiolillo.domain.entities.models.FilmEditDTO;
import com.sergiolillo.domain.entities.models.FilmShortDTO;
import com.sergiolillo.exceptions.BadRequestException;
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
	public <T> List<T> getByProjection(@NonNull Class<T> type) {
		return repoFilm.findAllBy(type);
	}

	@Override
	public <T> List<T> getByProjection(@NonNull Sort sort, @NonNull Class<T> type) {
		return repoFilm.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(@NonNull Pageable pageable, @NonNull Class<T> type) {
		return repoFilm.findAllBy(pageable, type);
	}
	

	@Override
	public List<Film> getAll(@NonNull Sort sort) {
		return repoFilm.findAll(sort);
	}
	
	@Override
	public Page<Film> getAll(@NonNull Pageable pageable) {
		return repoFilm.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return repoFilm.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return repoFilm.findById(id);
	}
	
	@Override
	public Optional<Film> getOne(@NonNull Specification<Film> spec) {
		return repoFilm.findOne(spec);
	}

	@Override
	public List<Film> getAll(@NonNull Specification<Film> spec) {
		return repoFilm.findAll(spec);
	}

	@Override
	public Page<Film> getAll(@NonNull Specification<Film> spec, @NonNull Pageable pageable) {
		return repoFilm.findAll(spec, pageable);
	}

	@Override
	public List<Film> getAll(@NonNull Specification<Film> spec, @NonNull Sort sort) {
		return repoFilm.findAll(spec, sort);
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
	public Page<FilmShortDTO> newFilmsAfterDate(@NonNull LocalDateTime fecha, @NonNull Pageable pageable) throws NotFoundException, InvalidDataException, MethodArgumentTypeMismatchException {
		Timestamp timestamp = Timestamp.valueOf(fecha);

		return repoFilm.findByLastUpdateGreaterThanEqualOrderByLastUpdate(timestamp, pageable);
	}
	
	@Override
	public Page<FilmDetailsDTO> searchFilmsByTitle(String query, Pageable pageable) {
		var queryLower = query.toLowerCase();
	    return repoFilm.findByTitleContaining(queryLower, pageable).map(FilmDetailsDTO::from);
	}


}
