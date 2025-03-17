package com.sergiolillo.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.sergiolillo.domain.core.entities.AbstractEntity;


/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name="language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
public class Language extends AbstractEntity<Language> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="language_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int languageId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

	@Column(nullable=false, length=20)
	@NotBlank(message="El nombre no puede estar vacío ni ser nulo")
	@Size(max=20, min=2, message="La longitud del nombre debe estar entre 2 y 20 caracteres")
	@Pattern(regexp="^[A-ZÀ-Ö ]+$", message="El nombre debe estar en mayúsculas")
	private String name;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="language", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Film> films;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="languageVO", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Film> filmsVO;

	public Language() {
	}
	
	

	public Language(int languageId, String name) {
		super();
		this.languageId = languageId;
	    this.lastUpdate = new Timestamp(System.currentTimeMillis()); 
		this.name = name;
	}



	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "Language [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(languageId, lastUpdate, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		return languageId == other.languageId && Objects.equals(lastUpdate, other.lastUpdate)
				&& Objects.equals(name, other.name);
	}


}