package com.sergiolillo.application.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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

import com.sergiolillo.domain.contracts.services.CategoryService;
import com.sergiolillo.domain.entities.Category;
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
@RequestMapping("/category/v1")
@Tag(name="Category Controller", description="Controller for category entity")
public class CategoryController {
	
	CategoryService srv;

	public CategoryController(CategoryService srv) {
		super();
		this.srv = srv;
	}
	
	@GetMapping("/")
	@Operation(summary="Gets all categories")
	public List<Category> getAll() {
		return srv.getAll();
	}
	
	@GetMapping(path= "{id}")
	@Operation(summary="Gets a category by ID")
	public Optional<Category> getOne(@PathVariable @Parameter(description="Category ID") int id) {
		return srv.getOne(id);
	}
	
	@GetMapping("/last")
	@Operation(summary="Get all categories ordered from newest to oldest")
	public List<Category> getAllOrderedByName() {
		return srv.findNovedades();
	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", description = "Category created",
		    content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Category.class),
		    examples = {
		        @ExampleObject(name = "Example OK", value = "{\"ultimaModificacion\": \"2025-12-01 13:00:21\", \"categoría\": \"Acción\"}"),
		    })
	)
	@Operation(summary="Creates a category based in its name")
	public ResponseEntity<Category> create(@RequestBody @Valid Category category) throws BadRequestException, DuplicateKeyException, InvalidDataException {
				
		var newCategory = srv.add(category);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}") 
		.buildAndExpand(newCategory.getCategoryId()).toUri(); 

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	@Operation(summary="Modifies a category")
	public void update(@RequestBody @Valid Category category) throws NotFoundException, InvalidDataException {
		srv.modify(category);
	}
	
	@DeleteMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary="Deletes a category by its ID")
	public void delete(@PathVariable int id) throws NotFoundException, InvalidDataException { 
		srv.deleteById(id);
	}
	

}
