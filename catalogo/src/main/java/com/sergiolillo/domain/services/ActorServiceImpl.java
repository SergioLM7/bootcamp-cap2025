package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sergiolillo.domain.contracts.repositories.ActorRepository;
import com.sergiolillo.domain.contracts.services.ActorService;
import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;



@Service
public class ActorServiceImpl implements ActorService {
	
	private ActorRepository dao;
	
	public ActorServiceImpl(ActorRepository dao) {
		this.dao = dao;
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Actor> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}
	
	@Override
	public List<Actor> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("El actor no puede ser nulo.");
		}
		
		if(item.getActorId() > 0 && dao.existsById(item.getActorId())) {
			throw new DuplicateKeyException("El actor ya existe.");
		}
		
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("El actor no puede ser nulo.");
		}
		
		if(!dao.existsById(item.getActorId())) {
			throw new NotFoundException("El actor no existe.");
		}
		
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());

		return dao.save(item);
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		if(!dao.findById(item.getActorId()).isPresent() || item == null) {
			throw new InvalidDataException("El actor no existe.");
		}
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException {
		if(!dao.findById(id).isPresent()) {
			throw new NotFoundException("El actor no existe.");
		}
		
		dao.deleteById(id);
	}


}
