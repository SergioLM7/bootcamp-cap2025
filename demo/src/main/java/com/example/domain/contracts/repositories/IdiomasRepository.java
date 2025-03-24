package com.example.domain.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


import com.example.domain.entities.Language;

@RepositoryRestResource(path="idiomas", collectionResourceRel="idiomas", itemResourceRel="idioma")
public interface IdiomasRepository extends JpaRepository<Language, Integer> {
	@RestResource(path="por nombre")
	List<Language> findTop5ByNameStartingWithOrderByNameAsc(String prefijo);
		
	@Override
	@RestResource(exported=false)
	void deleteById(Integer id);
}
