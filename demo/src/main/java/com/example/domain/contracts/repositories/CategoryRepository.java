package com.example.domain.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {

}
