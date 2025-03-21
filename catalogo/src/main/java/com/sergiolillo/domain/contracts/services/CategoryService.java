package com.sergiolillo.domain.contracts.services;

import java.util.List;

import com.sergiolillo.domain.core.contracts.services.DomainService;
import com.sergiolillo.domain.entities.Category;

public interface CategoryService extends DomainService<Category, Integer>{
	List<Category> findNovedades();
}
