package com.sergiolillo.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	@JsonProperty("id")
	private int categoryId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonProperty("lastUpdate")
	private Timestamp lastUpdate;

	@Column(nullable=false, length=25)
	@NotBlank(message="El nombre no puede estar vacío ni ser nulo")
	@Size(max=45, min=2, message="La longitud del nombre debe estar entre 2 y 45 caracteres")
	@Pattern(regexp="^[A-ZÀ-Ö][a-zà-ö -]+$", message="El nombre debe tener una primera letra mayúscula y el resto, minúsculas. No acepta números")
	@JsonProperty("category")
	private String name;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="category")
	@JsonIgnore
	private List<FilmCategory> filmCategories;

	public Category() {
	}
	
	public Category(int categoryId) {
		this.categoryId = categoryId;
	}

	public Category(int categoryId, @NotBlank @Size(max=45, min=2) @Pattern(regexp="^[A-ZÀ-Ö][a-zà-ö -]+$") String name) {
		this.categoryId = categoryId;
		this.name = name;
		this.lastUpdate = new Timestamp(System.currentTimeMillis());
	}


	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}
	
	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name
				+  ", lastUpdate=" + lastUpdate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Category o)
			return categoryId == o.categoryId;
		else
			return false;
	}
	

}