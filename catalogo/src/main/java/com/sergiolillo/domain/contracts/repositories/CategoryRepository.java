package com.sergiolillo.domain.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergiolillo.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
   
}
