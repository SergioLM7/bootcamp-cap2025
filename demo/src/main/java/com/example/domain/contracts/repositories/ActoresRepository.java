package com.example.domain.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Actor;

public interface ActoresRepository extends JpaRepository<Actor, Integer> {

}
