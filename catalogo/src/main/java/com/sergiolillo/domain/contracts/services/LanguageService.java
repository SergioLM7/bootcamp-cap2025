package com.sergiolillo.domain.contracts.services;

import java.util.List;

import com.sergiolillo.domain.core.contracts.services.DomainService;
import com.sergiolillo.domain.entities.Language;


public interface LanguageService extends DomainService<Language, Integer>{
	List<Language> findAllByOrderByName();

}
