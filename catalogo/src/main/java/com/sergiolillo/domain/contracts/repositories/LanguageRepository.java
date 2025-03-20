package com.sergiolillo.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.sergiolillo.domain.entities.Language;


public interface LanguageRepository extends ListCrudRepository<Language, Integer> {
	
	List<Language> findAllByOrderByName();
	
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
