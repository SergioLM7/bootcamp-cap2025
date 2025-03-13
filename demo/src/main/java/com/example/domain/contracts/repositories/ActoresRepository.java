package com.example.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.entities.Actor;

public interface ActoresRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {

	List<Actor> findTop100ByFirstNameStartingWithOrderByLastNameDesc (String prefijo);
	List<Actor> findTop100ByFirstNameStartingWith (String prefijo, Sort orderBy);
	
	List<Actor> findByActorIdGreaterThan (int id);
	
	//Pide la instancia en base a una propiedad de la instancia
	@Query(value="SELECT a FROM Actor a WHERE a.actorId > ?1")
	List<Actor> findNovedadesJPQL (int id);
	
	//Pide la fila en base a una columna de la tabla
	@Query(value="SELECT a FROM actor a WHERE a.actor_id > :id", nativeQuery=true)
	List<Actor> findNovedadesSQL (int id);

}
