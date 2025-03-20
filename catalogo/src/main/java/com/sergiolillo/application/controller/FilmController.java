package com.sergiolillo.application.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiolillo.domain.contracts.services.FilmService;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;
import com.sergiolillo.domain.entities.models.FilmShortDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/film/v1")
@Tag(name="Películas Controller", description="Controller for films")
public class FilmController {
	
	private FilmService srv;
	
	public FilmController(FilmService srv) {
		super();
		this.srv = srv;
	}
	
	@GetMapping
	public List<FilmShortDTO> getAll() {
		return srv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping (params = {"page"})
	@Operation(summary="Gets all films but paginated")
	public Page<FilmShortDTO> getAll(@ParameterObject Pageable pageable) { 
		return srv.getByProjection(pageable, FilmShortDTO.class);
	} 
	
	@GetMapping(path = "/{id}") 
	@Operation(summary="Gets a film details by its ID")
	public FilmDetailsDTO getOne(@PathVariable @Parameter(description="Film ID") int id) { 
		var item = srv.getOne(id);
		return FilmDetailsDTO.from(item.get());
	}
	
    @GetMapping("/search")
	@Operation(summary="Search a film by user input",     
	parameters = {
	        @Parameter(name = "query", description = "The title search query", required = true),
	        @Parameter(name = "page", description = "Page number to fetch", required = false, example = "0"),
	        @Parameter(name = "size", description = "Number of results per page", required = false, example = "10")
	})
    public Page<FilmDetailsDTO> searchFilms(@RequestParam String query, @ParameterObject Pageable pageable) {
        return srv.searchFilmsByTitle(query, pageable);
    }

	

}
