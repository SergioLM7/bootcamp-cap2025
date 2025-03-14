package com.sergiolillo.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sergiolillo.domain.contracts.repositories.ActoresRepository;
import com.sergiolillo.domain.contracts.services.ActoresService;
import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

@Service
public class ActoresServiceImpl implements ActoresService {
	
	private ActoresRepository dao;
	
	public ActoresServiceImpl(ActoresRepository dao) {
		this.dao = dao;
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
		
		//Compruebo primero si el id de lo que me está llegando es mayor que cero
		// si no, ni consulto a la BBDD para no gastar recursos para nada
		if(item.getActorId() > 0 && dao.existsById(item.getActorId())) {
			throw new DuplicateKeyException("El actor ya existe.");
		}
		
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repartePremios() {
		// TODO Auto-generated method stub
		
	}

}
