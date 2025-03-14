package com.example.domain.entities.DTO;

import com.example.domain.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data @AllArgsConstructor
public class ActorDTO {

	private int actorId;
	private String firstName;
	private String lastName;
	
	//Para pasar de entidad a DTO, serialización 
	public static ActorDTO from(Actor source){
		return new ActorDTO(source.getActorId(), source.getFirstName(), source.getLastName());
	}
	
	//Para pasar de DTO a entidad, DESserialización 
	public static Actor from(ActorDTO source){
		return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
	}
}
