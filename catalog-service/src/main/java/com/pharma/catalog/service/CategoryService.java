package com.pharma.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.catalog.dto.CategoryDTO;
import com.pharma.catalog.entity.Category;
import com.pharma.catalog.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	 public List<CategoryDTO> getAllCategories() {
	        return categoryRepository.findAll()
	                .stream()
	                .map(this::toDTO)
	                .collect(Collectors.toList());
	    }

	    public CategoryDTO getCategoryById(Long id) {
	        Category category = categoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
	        return toDTO(category);
	    }

	    public CategoryDTO createCategory(CategoryDTO dto) {
	        if (categoryRepository.existsByCatName(dto.getCategoryname())) {
	            throw new RuntimeException("Category already exists: " + dto.getCategoryname());
	        }
	        Category category = toEntity(dto);
	        Category saved = categoryRepository.save(category);
	        return toDTO(saved);
	    }

	    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
	        Category existing = categoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
	        existing.setCatName(dto.getCategoryname());
	        return toDTO(categoryRepository.save(existing));
	    }

	    public void deleteCategory(Long id) {
	        if (!categoryRepository.existsById(id)) {
	            throw new RuntimeException("Category not found with id: " + id);
	        }
	        categoryRepository.deleteById(id);
	    }
	private CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCatName(category.getCatName());
        dto.setMedicineCount(
                category.getMedicines() != null ? category.getMedicines().size() : 0
        );
        return dto;
    }
	private Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setCatName(dto.getCategoryname());
        return category;
    }
	
}
