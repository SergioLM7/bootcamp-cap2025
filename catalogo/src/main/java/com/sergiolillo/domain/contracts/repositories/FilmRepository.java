package com.sergiolillo.domain.contracts.repositories;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sergiolillo.domain.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.models.FilmShortDTO;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer>{
	Page<FilmShortDTO> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha, Pageable pageable);
    Page<Film> findByTitleContaining(String title, Pageable pageable);

}
