package com.sergiolillo.domain.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sergiolillo.domain.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer>{
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
    Page<Film> findByTitleContaining(String title, Pageable pageable);

}
