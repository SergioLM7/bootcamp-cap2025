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
	
}
