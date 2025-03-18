package com.sergiolillo.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sergiolillo.domain.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>{

	@Query(value="SELECT f FROM Film f JOIN FETCH f.filmActors ORDER BY f.lastUpdate DESC")
	List<Film> findNovedades();
}
