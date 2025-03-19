package com.sergiolillo.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sergiolillo.domain.core.entities.AbstractEntity;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film extends AbstractEntity<Film> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static enum Rating {
		GENERAL_AUDIENCES("G"), 
		PARENTAL_GUIDANCE_SUGGESTED("PG"), 
		PARENTS_STRONGLY_CAUTIONED("PG-13"), 
		RESTRICTED("R"),
		ADULTS_ONLY("NC-17");

		String value;

		Rating(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
		
		public static Rating getEnum(String value) {
			switch (value) {
			case "G":
				return Rating.GENERAL_AUDIENCES;
			case "PG":
				return Rating.PARENTAL_GUIDANCE_SUGGESTED;
			case "PG-13":
				return Rating.PARENTS_STRONGLY_CAUTIONED;
			case "R":
				return Rating.RESTRICTED;
			case "NC-17":
				return Rating.ADULTS_ONLY;
			case "":
				return null;
			default:
				throw new IllegalArgumentException("Unexpected value: " + value);
			}
		}

		public static final String[] VALUES = { "G", "PG", "PG-13", "R", "NC-17" };
	}
	
	@Converter
	private static class RatingConverter implements AttributeConverter<Rating, String> {
		@Override
		public String convertToDatabaseColumn(Rating rating) {
			return rating == null ? null : rating.getValue();
		}

		@Override
		public Rating convertToEntityAttribute(String value) {
			return value == null ? null : Rating.getEnum(value);
		}
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="film_id", unique=true, nullable=false)
	private int filmId;

	@Lob
	private String description;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;
	
	@Positive
	private int length;

	@Convert(converter = RatingConverter.class)
	private Rating rating;

	@Column(name="release_year")
	@Min(1900)
	@Max(2155)
	private Short releaseYear;

	@Column(name="rental_duration", nullable=false)
	@NotNull(message="La rentalDuration no puede ser nula")
	@DecimalMin(value = "0.0", inclusive = false, message="El rentalDuration no puede ser negativo ni 0.0")
	private byte rentalDuration;

	@Column(name="rental_rate", nullable=false, precision=10, scale=2)
	@NotNull(message="El rentalRate no puede ser nulo")
	@Positive
	private BigDecimal rentalRate;

	@Column(name="replacement_cost", nullable=false, precision=10, scale=2)
	@NotNull(message="El replacementCost no puede ser nulo")
	@DecimalMin(value = "0.0", inclusive = false, message="El replacementCost no puede ser negativo ni 0.0")
	private BigDecimal replacementCost;

	@Column(nullable=false, length=128)
	@NotBlank(message="El title no puede ser nulo ni estar vacío")
	@Size(max=128, min=2, message="La longitud del título debe estar entre 2 y 128 caracteres")
	private String title;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="language_id", nullable=false)
	@NotNull
	private Language language;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="original_language_id")
	private Language languageVO;

	//bi-directional many-to-one association to FilmActor
	//Cuando se guarde la película, si hay cambios en el filmActor, también los aplicará
	//El orphanRemoval le dice que si va a borrar la película, primero borre las relaciones
	@OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FilmActor> filmActors = new ArrayList<FilmActor>();

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FilmCategory> filmCategories = new ArrayList<FilmCategory>();

	public Film() {
	}
	
	public Film(int filmId) {
		this.filmId = filmId;
	}
	
	public Film(int filmId, @NotBlank @Size(max=128, min=2) String title, @NotNull Language language,
			@NotNull @Positive byte rentalDuration,
			@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal rentalRate,
			@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal replacementCost) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.language = language;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
	}

	public Film(int filmId, @NotBlank  @Size(max=128, min=2 )String title, String description, @Min(1900) @Max(2155) Short releaseYear,
			@NotNull Language language, Language languageVO, @Positive byte rentalDuration,
			@Positive @DecimalMin(value = "0.0", inclusive = false) BigDecimal rentalRate,
			@Positive Integer length,
			@DecimalMin(value = "0.0", inclusive = false)BigDecimal replacementCost,
			Rating rating) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.language = language;
		this.languageVO = languageVO;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replacementCost = replacementCost;
		this.rating = rating;
	}



	// TODO : ENUM de rating


	public int getFilmId() {
		return this.filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Short getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(Short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public byte getRentalDuration() {
		return this.rentalDuration;
	}

	public void setRentalDuration(byte rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return this.rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public BigDecimal getReplacementCost() {
		return this.replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getLanguageVO() {
		return this.languageVO;
	}

	public void setLanguageVO(Language languageVO) {
		this.languageVO = languageVO;
	}

	// GESTION DE ACTORES 
	
	public List<Actor> getActors() {
		return this.filmActors.stream().map(item -> item.getActor()).toList();
	}
	
	public void setActors(List<Actor> source) {
		if(filmActors == null || filmActors.isEmpty()) {
			clearActors();
			source.forEach(item -> addActor(item));
		}
	}
	
	public void clearActors() {
		filmActors = new ArrayList<FilmActor>();
	}
	
	public void addActor(Actor actor) {
		FilmActor filmActor = new FilmActor (this, actor);
		filmActors.add(filmActor);
	}
	
	public void removeActor(Actor actor) {
		var filmActor = filmActors.stream().filter(item -> item.getActor().equals(actor)).findFirst();
		
		if (filmActor.isEmpty())
			return;
		
		filmActors.remove(filmActor.get());
	}
	
	public void removeActor(int actorId) {
		removeActor(new Actor(actorId));
	}
	
	// Gestión de categorias

	public List<Category> getCategories() {
		return this.filmCategories.stream().map(item -> item.getCategory()).toList();
	}
	
	public void setCategories(List<Category> source) {
		if (filmCategories == null || !filmCategories.isEmpty())
			clearCategories();
		source.forEach(item -> addCategory(item));
	}
	
	public void clearCategories() {
		filmCategories = new ArrayList<FilmCategory>();
	}

	public void addCategory(Category item) {
		FilmCategory filmCategory = new FilmCategory(this, item);
		filmCategories.add(filmCategory);
	}
	
	public void addCategory(int id) {
		addCategory(new Category(id));
	}
	
	public void removeCategory(Category ele) {
		var filmCategory = filmCategories.stream().filter(item -> item.getCategory().equals(ele)).findFirst();
		if (filmCategory.isEmpty())
			return;
		filmCategories.remove(filmCategory.get());
	}

	public void removeCategory(int id) {
		removeCategory(new Category(id));
	}


	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", description=" + description + ", lastUpdate=" + lastUpdate + ", length="
				+ length + ", rating=" + rating + ", releaseYear=" + releaseYear + ", rentalDuration=" + rentalDuration
				+ ", rentalRate=" + rentalRate + ", replacementCost=" + replacementCost + ", title=" + title
				+ ", language=" + language + ", languageVO=" + languageVO + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(filmId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Film o)
			return filmId == o.filmId;
		else
			return false;
	}
	
	public Film merge(Film target) {
//		BeanUtils.copyProperties(this, target, "filmId" , "filmActors", "filmCategories");
		target.title = title;
		target.description = description;
		target.releaseYear = releaseYear;
		target.language = language;
		target.languageVO = languageVO;
		target.rentalDuration = rentalDuration;
		target.rentalRate = rentalRate;
		target.length = length;
		target.replacementCost = replacementCost;
		target.rating = rating;
		// Borra los actores que sobran
		target.getActors().stream().filter(item -> !getActors().contains(item))
				.forEach(item -> target.removeActor(item));
		// Añade los actores que faltan
		getActors().stream().filter(item -> !target.getActors().contains(item)).forEach(item -> target.addActor(item));
		// Borra las categorias que sobran
		target.getCategories().stream().filter(item -> !getCategories().contains(item))
				.forEach(item -> target.removeCategory(item));
		// Añade las categorias que faltan
		getCategories().stream().filter(item -> !target.getCategories().contains(item))
				.forEach(item -> target.addCategory(item));
		
		// Bug de Hibernate
		target.filmActors.forEach(o -> o.prePersiste());
		target.filmCategories.forEach(o -> o.prePersiste());
		
		return target;
	}
	
	// Bug de Hibernate
	@PostPersist
	@PostUpdate
	public void prePersiste() {
		System.err.println("prePersiste(): Bug Hibernate");
		filmActors.forEach(o -> o.prePersiste());
		filmCategories.forEach(o -> o.prePersiste());
	}
 }