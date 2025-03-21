package com.sergiolillo.domain.contracts.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sergiolillo.domain.core.contracts.services.ProjectionDomainService;
import com.sergiolillo.domain.core.contracts.services.SpecificationDomainService;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;
import com.sergiolillo.domain.entities.models.FilmShortDTO;
import com.sergiolillo.exceptions.BadRequestException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

public interface FilmService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer>{
	Page<FilmShortDTO> newFilmsAfterDate(LocalDateTime fecha, Pageable pageable) throws NotFoundException, InvalidDataException, BadRequestException;
    Page<FilmDetailsDTO> searchFilmsByTitle(String title, Pageable pageable);
}
