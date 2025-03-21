package com.sergiolillo.application.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sergiolillo.domain.contracts.services.FilmService;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;
import com.sergiolillo.domain.entities.models.FilmShortDTO;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/film/v1")
@Tag(name="Film Controller", description="Controller for film entity")
public class FilmController {
	
	private FilmService srv;
	
	public FilmController(FilmService srv) {
		super();
		this.srv = srv;
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
	
//	@PostMapping
//	@ApiResponse(responseCode = "201", description = "Film created")
//	public ResponseEntity<Object> create(@Valid @RequestBody FilmDetailsDTO item) throws DuplicateKeyException, InvalidDataException {
//		
//		var newItem = srv.add(FilmDetailsDTO.from(item));
//			
//
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}") 
//				.buildAndExpand(newItem.getActorId()).toUri(); 
//		
//		return ResponseEntity.created(location).build(); 
//	} 
	
    @GetMapping("/search")
	@Operation(summary="Search a film by its title",     
	parameters = {
	        @Parameter(name = "query", description = "The title search query. Could be an incompleted title", required = true),
	        @Parameter(name = "page", description = "Page number to fetch", required = false, example = "0"),
	        @Parameter(name = "size", description = "Number of results per page", required = false, example = "5")
	})
    public Page<FilmDetailsDTO> searchFilms(@RequestParam String query, @ParameterObject Pageable pageable) {
        return srv.searchFilmsByTitle(query, pageable);
    }
    
    
    @GetMapping("/newFilms")
	@Operation(summary="Show films added after a date")
   public Page<FilmDetailsDTO> searchLastFilmsAddedAfterDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Timestamp fecha, @ParameterObject Pageable pageable){
	   
    	 return srv.newFilmsAfterDate(fecha, pageable);
   }
    
    
	@DeleteMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void delete(@PathVariable int id) throws NotFoundException, InvalidDataException { 
		srv.deleteById(id);
	}

}
