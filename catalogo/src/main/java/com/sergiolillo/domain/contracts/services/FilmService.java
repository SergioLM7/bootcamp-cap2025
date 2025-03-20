package com.sergiolillo.domain.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sergiolillo.domain.core.contracts.services.ProjectionDomainService;
import com.sergiolillo.domain.core.contracts.services.SpecificationDomainService;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;

public interface FilmService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer>{
	List<Film> novedades(Timestamp fecha);
    Page<FilmDetailsDTO> searchFilmsByTitle(String title, Pageable pageable);
}
