package com.sergiolillo.domain.contracts.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sergiolillo.domain.core.contracts.services.ProjectionDomainService;
import com.sergiolillo.domain.entities.Actor;
import com.sergiolillo.domain.entities.models.ActorDTO;

public interface ActorService extends ProjectionDomainService<Actor, Integer> {
    Page<ActorDTO> searchActorByTitle(String name, Pageable pageable);

}
