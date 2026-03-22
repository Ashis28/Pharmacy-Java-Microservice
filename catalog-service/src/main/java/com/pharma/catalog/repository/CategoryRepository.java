package com.pharma.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.catalog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{

	Optional<Category>findByCatName(String name);
	boolean existsByCatName(String catName);
}
