package com.sergiolillo.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sergiolillo.domain.entities.Language;


public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>{
	
	List<Language> findTop15ByNameStartingWithOrderByNameDesc (String prefijo);
	List<Language> findTop15ByNameStartingWith (String prefijo, Sort orderBy);
	
	@Query(value="SELECT l FROM Language l ORDER BY l.lastUpdate DESC")
	List<Language> findNovedades();
	
    @Query(value = "SELECT l.language_id, l.name, COUNT(f.film_id) AS film_count " +
            "FROM film f " +
            "JOIN language l ON f.language_id = l.language_id " +
            "GROUP BY l.language_id " +
            "ORDER BY film_count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5LanguagesByFilmCount();
}
