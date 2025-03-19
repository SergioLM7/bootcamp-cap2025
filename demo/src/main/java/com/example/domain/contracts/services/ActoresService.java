package com.example.domain.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.domain.core.contracts.services.ProjectionDomainService;
import com.example.domain.entities.Actor;

public interface ActoresService extends ProjectionDomainService<Actor, Integer> {

	void repartePremios();
	List<Actor> novedades(Timestamp fecha);
	
}
