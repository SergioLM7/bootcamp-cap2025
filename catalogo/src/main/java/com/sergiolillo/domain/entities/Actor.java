package com.sergiolillo.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.sergiolillo.domain.core.entities.AbstractEntity;


/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
public class Actor extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false)
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	@NotBlank(message="El nombre no puede estar vacío ni ser nulo")
	@Size(max=45, min=2, message="La longitud del nombre debe estar entre 2 y 45 caracteres")
	@Pattern(regexp="^[A-ZÀ-Ö ]+$", message="El nombre debe estar en mayúsculas")
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	@NotBlank(message="El apellido no puede estar vacío ni ser nulo")
	@Size(max=45, min=2, message="La longitud del apellido debe estar entre 2 y 45 caracteres")
	@Pattern(regexp="^[A-ZÀ-Ö ]+$", message="El apellido debe estar en mayúsculas")
	private String lastName;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@PastOrPresent
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy="actor", fetch= FetchType.LAZY)
	private List<FilmActor> filmActors;

	public Actor() {
	}
	
	public Actor(int actorId, String firstName, String lastName) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Actor(int actorId) {
		super();
		this.actorId = actorId;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	//Si tuviésemos propiedades para hacer esto...
	public void jubilate() {
		//change active to false
		//jubilation date to currente date
	}
	
	public void premioRecibido(String premio) {
		//...
	}

}