package com.sergiolillo.application.controller;

import java.net.URI;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sergiolillo.domain.contracts.services.ActorService;
import com.sergiolillo.domain.entities.models.ActorDTO;
import com.sergiolillo.exceptions.BadRequestException;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/actor/v1")
@Tag(name="Actor Controller", description="Controller for Actor entity")
public class ActorController {
	
	private ActorService srv;
	
	public ActorController(ActorService srv) {
		super();
		this.srv = srv;
	}
	
	@GetMapping 
	@Hidden
	public List<ActorDTO> getAll() { 
		return srv.getByProjection(ActorDTO.class);
	} 
	
	@GetMapping (params = {"page"})
	@Operation(summary="Gets all actors but paged")
	public Page<ActorDTO> getAll(@ParameterObject Pageable pageable) { 
		return srv.getByProjection(pageable, ActorDTO.class);
	} 
		
	@GetMapping(path = "/{id}") 
	@Operation(summary="Gets an actor by its ID")
	public ActorDTO getOne(@PathVariable @Parameter(description="Actor ID") int id) throws NotFoundException { 
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException("No se encontró el actor con id " + id);
		
		return ActorDTO.from(item.get());
	}
	
	record Titulo(int id, String titulo) { }
	
	@GetMapping(path = "/{id}/films")
	@Operation(summary="Gets films related with an actor (by its ID)")
	@Transactional
	public List<Titulo> getPeliculas(@PathVariable int id) throws NotFoundException { 
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException("No se encontró el actor con id " + id);
		
		return item.get().getFilmActors().stream().map( o -> new Titulo(o.getFilm().getFilmId(), o.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", description = "Actor creado")
	@Operation(summary="Creates an actor")
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		var newItem = srv.add(ActorDTO.from(item));
			
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") 
				.buildAndExpand(newItem.getActorId()).toUri(); 
		
		return ResponseEntity.created(location).build(); 
	} 
	
	@PutMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@Operation(summary="Modifies an actor by its ID")
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException { 
		if(item.getActorId() != id) {
			throw new BadRequestException("El id del actor no coincide con el recruso a modificar");
		}
		
		srv.modify(ActorDTO.from(item));
	} 
	
	@DeleteMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@Operation(summary="Deletes an actor by its ID")
	public void delete(@PathVariable int id) throws NotFoundException, InvalidDataException { 
		srv.deleteById(id);
	}
	
    @GetMapping("/search")
	@Operation(summary="Search an actor by its title",     
	parameters = {
	        @Parameter(name = "query", description = "The title search query. Could be an incompleted title", required = true),
	        @Parameter(name = "page", description = "Page number to fetch", required = false, example = "0"),
	        @Parameter(name = "size", description = "Number of results per page", required = false, example = "5")
	})
    public Page<ActorDTO> searcActors(@RequestParam String query, @ParameterObject Pageable pageable) {
        return srv.searchActorByTitle(query, pageable);
    }

}