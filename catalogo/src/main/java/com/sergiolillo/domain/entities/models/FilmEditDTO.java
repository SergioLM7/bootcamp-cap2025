package com.sergiolillo.domain.entities.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sergiolillo.domain.entities.Film;
import com.sergiolillo.domain.entities.Language;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Film (Edit)", description = "Editable version of film")
@Data @AllArgsConstructor @NoArgsConstructor
public class FilmEditDTO {
	@Schema(description = "Film identifier", accessMode = AccessMode.READ_ONLY)
	private int filmId;
	@Schema(description = "Short description about film argument", minLength = 2)
	private String description;
	@Schema(description = "Film duration, in minutes", minimum = "0", exclusiveMinimum = true)
	private Integer length;
	@Schema(description = "The age rating assigned to the film", allowableValues = {"G", "PG", "PG-13", "R", "NC-17"})
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;
	@Schema(description = "Film year release", minimum = "1901", maximum = "2155")
	private Short releaseYear;
	@Schema(description = "The length of the rental period, in days", minimum = "0", exclusiveMinimum = true)
	@NotNull
	private Byte rentalDuration;
	@Schema(description = "The cost of renting the film for the established period of time", minimum = "0", exclusiveMinimum = true)
	@NotNull
	private BigDecimal rentalRate;
	@Schema(description = "The cost if the film is not returned or is lost", minimum = "0", exclusiveMinimum = true)
	@NotNull
	private BigDecimal replacementCost;
	@Schema(description = "Film title")
	@NotBlank
	@Size(min=2, max = 128)
	private String title;
	@Schema(description = "Film language identifier")
	@NotNull
	private Integer languageId;
	@Schema(description = "Film VO language identifier")
	private Integer languageVOId;
	@Schema(description = "List of actors identifiers associated with the film")
	@ArraySchema(uniqueItems = true, minItems = 1)
	private List<Integer> actors = new ArrayList<>();
	@Schema(description = "List of categories identifiers associated with the film")
	@ArraySchema(uniqueItems = true, minItems = 1, maxItems = 3)
	private List<Integer> categories = new ArrayList<>();

	public static FilmEditDTO from(Film source) {
		return new FilmEditDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating() == null ? null : source.getRating().getValue(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getLanguageId(),
				source.getActors().stream().map(item -> item.getActorId())
					.collect(Collectors.toList()),
				source.getCategories().stream().map(item -> item.getCategoryId())
					.collect(Collectors.toList())
				);
	}
	public static Film from(FilmEditDTO source) {
		Film rslt = new Film(
				source.getFilmId(), 
				source.getTitle(),
				source.getDescription(),
				source.getReleaseYear(),
				source.getLanguageId() == null ? null : new Language(source.getLanguageId()),
				source.getLanguageVOId() == null ? null : new Language(source.getLanguageVOId()),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getLength(),
				source.getReplacementCost(),
				source.getRating() == null ? null : Film.Rating.getEnum(source.getRating())
				);
		source.getActors().stream().forEach(item -> rslt.addActor(item));
		source.getCategories().stream().forEach(item -> rslt.addCategory(item));
		return rslt;
	}

}