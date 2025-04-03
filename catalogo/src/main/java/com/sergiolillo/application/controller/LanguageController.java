package com.sergiolillo.application.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.sergiolillo.domain.contracts.services.LanguageService;
import com.sergiolillo.domain.entities.Language;
import com.sergiolillo.exceptions.BadRequestException;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/language/v1")
@Tag(name="Language Controller", description="Controller for language entity")
public class LanguageController {
	
	private LanguageService srv;

	public LanguageController(LanguageService srv) {
		super();
		this.srv = srv;
	}
	
	@GetMapping("")
	@Operation(summary="Gets all languages")
	public List<Language> getAll() {
		return srv.getAll();
	}
	
	
	@GetMapping(path= "{id}")
	@Operation(summary="Gets a language by ID")
	public Optional<Language> getOne(@PathVariable @Parameter(description="Language ID") int id) {
		return srv.getOne(id);
	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", description = "Language created",
		    content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Language.class),
		    examples = {
		        @ExampleObject(name = "Example OK", value = "{\"ultimaModificacion\": \"2025-12-01 13:00:21\", \"idioma\": \"ENGLISH\"}"),
		    })
	)
	@Operation(summary="Creates a language")
	public ResponseEntity<Language> create(@RequestBody @Valid Language language) throws BadRequestException, DuplicateKeyException, InvalidDataException {
				
		var newLanguage = srv.add(language);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}") 
		.buildAndExpand(newLanguage.getLanguageId()).toUri(); 

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	@Operation(summary="Modifies a language")
	public void update(@RequestBody @Valid Language language) throws NotFoundException, InvalidDataException {
		srv.modify(language);
	}
	
	
	@DeleteMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary="Deletes a language by its ID")
	public void delete(@PathVariable int id) throws NotFoundException, InvalidDataException { 
		srv.deleteById(id);
	}
	
	@GetMapping("/order")
	@Operation(summary="Get all language ordered alphabetically")
	public List<Language> getAllOrderedByName() {
		return srv.findAllByOrderByName();
	}
	
	

}
