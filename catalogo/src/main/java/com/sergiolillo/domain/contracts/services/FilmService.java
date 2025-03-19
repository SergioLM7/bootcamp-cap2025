package com.sergiolillo.domain.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.sergiolillo.domain.core.contracts.services.ProjectionDomainService;
import com.sergiolillo.domain.core.contracts.services.SpecificationDomainService;
import com.sergiolillo.domain.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer>{
	List<Film> novedades(Timestamp fecha);
}
