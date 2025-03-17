package com.sergiolillo.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sergiolillo.domain.entities.Actor;

public interface ActoresRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {

	List<Actor> findTop100ByFirstNameStartingWithOrderByLastNameDesc (String prefijo);
	List<Actor> findTop100ByFirstNameStartingWith (String prefijo, Sort orderBy);
	
	List<Actor> findByActorIdGreaterThan (int id);
	
	//Pide la fila en base a una columna de la tabla
	@Query(value="SELECT * FROM actor WHERE actor_id > :id ORDERBY DESC", nativeQuery=true)
	List<Actor> findNovedadesSQL (int id);

}
