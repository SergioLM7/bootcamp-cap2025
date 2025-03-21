package com.sergiolillo.domain.entities.models;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShortDTO {
	@Value("#{target.actorId}")
	int getId();
	@Value("#{target.lastName + ', ' + target.firstName}")
	String getNombre();
}
