package com.example.domain.entities.DTO;

import org.springframework.data.rest.core.config.Projection;

import com.example.domain.entities.Category;
import com.example.domain.entities.Language;

@Projection(name="name-only", types= {Language.class, Category.class})
public interface NameOnly {
	String getName();
}
