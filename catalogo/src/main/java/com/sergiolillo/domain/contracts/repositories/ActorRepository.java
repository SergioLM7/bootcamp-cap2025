package com.sergiolillo.domain.contracts.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sergiolillo.domain.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.sergiolillo.domain.entities.Actor;

public interface ActorRepository extends ProjectionsAndSpecificationJpaRepository<Actor, Integer> {
	@Query(value= "SELECT * FROM actor a WHERE a.first_name LIKE CONCAT ('%', :name, '%') OR a.last_name LIKE CONCAT ('%', :name, '%') ", nativeQuery= true)
    Page<Actor> searchFirstLastName(@Param("name") String name, Pageable pageable);

}
