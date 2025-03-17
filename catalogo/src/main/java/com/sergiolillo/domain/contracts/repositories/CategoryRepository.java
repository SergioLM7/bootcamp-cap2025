package com.sergiolillo.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sergiolillo.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
   
	List<Category> findTop15ByNameStartingWithOrderByNameDesc (String prefijo);
	List<Category> findTop15ByNameStartingWith (String prefijo, Sort orderBy);
	
	@Query(value="SELECT c FROM Category c JOIN FETCH c.filmCategories ORDER BY c.lastUpdate DESC")
	List<Category> findNovedades();
	
	//Pide la instancia en base a una propiedad de la instancia
//	@Query(value="SELECT a FROM Actor a WHERE a.actorId > ?1")
//	List<Category> findNovedadesJPQL (int id);
	
	//Pide la fila en base a una columna de la tabla
//	@Query(value="SELECT * FROM actor WHERE actor_id > :id", nativeQuery=true)
//	List<Category> findNovedadesSQL (int id);
}
