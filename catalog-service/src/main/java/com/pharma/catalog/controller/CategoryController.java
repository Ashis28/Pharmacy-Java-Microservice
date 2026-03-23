package com.pharma.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.catalog.dto.CategoryDTO;
import com.pharma.catalog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalog/categories")
public class CategoryController {

	@Autowired
	public CategoryService cs;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		return ResponseEntity.ok(cs.getAllCategories());
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
		return ResponseEntity.ok(cs.getCategoryById(id));
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(cs.createCategory(dto));	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
														@Valid @RequestBody CategoryDTO dto){
		return ResponseEntity.ok(cs.updateCategory(id, dto));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        cs.deleteCategory(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
	
}
