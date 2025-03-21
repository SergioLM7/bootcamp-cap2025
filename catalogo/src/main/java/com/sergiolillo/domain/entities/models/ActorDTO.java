package com.sergiolillo.domain.entities.models;

import com.sergiolillo.domain.entities.Actor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name="Actor", description="Datos de un actor.")
@Data @AllArgsConstructor
public class ActorDTO {

	
	private int actorId;
	
	@NotBlank
	@Size(min=2, max=45)
	@Pattern(regexp="^[A-Z]*$")
	@Schema(description = "Nombre del actor. Tiene que estar en mayúsculas.", example="BRUCE")
	private String firstName;
	
	@NotBlank
	@Size(min=2, max=45)
	@Schema(description = "Apellido del actor", example="Wayne")
	private String lastName;
	
	public static ActorDTO from(Actor source){
		return new ActorDTO(source.getActorId(), source.getFirstName(), source.getLastName());
	}
	
	public static Actor from(ActorDTO source){
		return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
	}
}
