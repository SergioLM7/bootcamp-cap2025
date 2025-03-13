package com.example.domain.contracts.services;

import com.example.domain.core.contracts.services.DomainService;
import com.example.domain.entities.Actor;

public interface ActoresService extends DomainService<Actor, Integer> {

	void repartePremios();
	
}
