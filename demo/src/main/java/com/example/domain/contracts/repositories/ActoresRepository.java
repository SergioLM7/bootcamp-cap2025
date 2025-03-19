package com.example.domain.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domain.entities.Actor;
import com.example.domain.entities.DTO.ActorDTO;
import com.example.domain.entities.DTO.ActorShort;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.NotFoundException;

public interface ActoresRepository extends ProjectionsAndSpecificationJpaRepository<Actor, Integer> {

	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	
	default Actor insert(Actor item) throws DuplicateKeyException {
		if(existsById(item.getActorId()))
			throw new DuplicateKeyException();
		return save(item);
	}
	
	default Actor update(Actor item) throws NotFoundException {
		if(!existsById(item.getActorId()))
			throw new NotFoundException();
		return save(item);
	}

}
