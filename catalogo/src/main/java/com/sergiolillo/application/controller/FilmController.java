package com.sergiolillo.application.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sergiolillo.domain.contracts.services.FilmService;
import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.models.ActorDTO;
import com.sergiolillo.domain.entities.models.FilmDetailsDTO;
import com.sergiolillo.domain.entities.models.FilmEditDTO;
import com.sergiolillo.domain.entities.models.FilmShortDTO;
import com.sergiolillo.exceptions.BadRequestException;
import com.sergiolillo.exceptions.DuplicateKeyException;
import com.sergiolillo.exceptions.InvalidDataException;
import com.sergiolillo.exceptions.NotFoundException;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/film/v1")
@Tag(name="Film Controller", description="Controller for Film entity")
public class FilmController {
	
	private FilmService srv;
	
	public FilmController(FilmService srv) {
		super();
		this.srv = srv;
	}
	
	@Hidden
	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam(defaultValue = "short") String mode) {
		return srv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping (params = {"page"})
	@Operation(summary="Gets all films but paginated")
	public Page<FilmShortDTO> getAll(@ParameterObject Pageable pageable) { 
		return srv.getByProjection(pageable, FilmShortDTO.class);
	} 
	
	@GetMapping("/{id}") 
	@Operation(summary="Gets a film details by its ID")
	public FilmDetailsDTO getOne(@PathVariable @Parameter(description="Film ID") int id) throws NotFoundException { 
		var item = srv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException("No se encontró la película con id " + id);
		
		return FilmDetailsDTO.from(item.get());
	}
	
	@PostMapping("/create")
	@ApiResponse(responseCode = "201", description = "Film created")
	@Operation(summary="Creates a film")
	public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO item) throws DuplicateKeyException, InvalidDataException {
		
		var newItem = srv.add(FilmEditDTO.from(item));
			

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") 
				.buildAndExpand(newItem.getFilmId()).toUri();
		
		return ResponseEntity.created(location).build(); 
	} 
	
	@PutMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@Operation(summary="Modifies a film by its ID")
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException { 
		if(item.getFilmId() != id) {
			throw new BadRequestException("El id de la película no coincide con el recruso a modificar");
		}
		
		srv.modify(FilmEditDTO.from(item));
	} 
	
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
   public Page<FilmShortDTO> searchLastFilmsAddedAfterDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fecha, @ParameterObject Pageable pageable) throws InvalidDataException, MethodArgumentTypeMismatchException, NotFoundException, BadRequestException{
	   
    	 return srv.newFilmsAfterDate(fecha, pageable);
   }
    
    
	@DeleteMapping("/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@Operation(summary="Deletes a film by its ID")
	public void delete(@PathVariable int id) throws NotFoundException, InvalidDataException { 
		srv.deleteById(id);
	}

}
