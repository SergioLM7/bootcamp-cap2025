package com.sergiolillo.domain.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.sergiolillo.domain.core.contracts.services.DomainService;
import com.sergiolillo.domain.entities.Film;

public interface FilmService extends DomainService<Film, Integer>{
	List<Film> novedades(Timestamp fecha);
}
