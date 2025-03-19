package com.example.application.controller;


import jakarta.validation.Valid; 


import java.net.URI;
import java.util.List;


import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseStatus; 
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;

import com.example.domain.contracts.services.ActoresService;
import com.example.domain.entities.DTO.ActorDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;



@RestController
@RequestMapping("/actores/v1")
public class ActoresController {
	
	private ActoresService srv;
	
	public ActoresController(ActoresService srv) {
		super();
		this.srv = srv;
	}
	
	
	@GetMapping 
	public List<ActorDTO> getAll() { 
		return srv.getByProjection(ActorDTO.class);
	} 
		
	@GetMapping(path = "/{id}") 
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException { 
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException("No se encontró el actor con id " + id);
		
		return ActorDTO.from(item.get());
	}
	
	@PostMapping 
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		
		var newItem = srv.add(ActorDTO.from(item));
			
		//Genero DINAMICAMENTE la nueva URL donde ha registrado el nuevo elemento añadido porque el convenio
		// pide que se devuelva un 201 con un Header donde aparezca la URL del nuevo elemtno
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") 
				.buildAndExpand(newItem.getActorId()).toUri(); 
		
		return ResponseEntity.created(location).build(); 
	} 
	
	@PutMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) 
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException { 
		if(item.getActorId() != id) {
			throw new BadRequestException("El id del actor no coincide con el recruso a modificar");
		}
		
		srv.modify(ActorDTO.from(item));
	} 
	
	@DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable int id) { 
		srv.deleteById(id);
	}

}
