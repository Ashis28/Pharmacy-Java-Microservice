package com.pharma.catalog.dto;

import jakarta.validation.constraints.NotBlank;


public class CategoryDTO {

	private Long categoryId;
	@NotBlank(message = "category name can't be null")
	private String categoryname;
	
	private int medicineCount;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCatName(String categoryname) {
		this.categoryname = categoryname;
	}

	public int getMedicineCount() {
		return medicineCount;
	}

	public void setMedicineCount(int medicineCount) {
		this.medicineCount = medicineCount;
	}

	public CategoryDTO(Long categoryId, String categoryname,int medicineCount) {
		super();
		this.categoryId = categoryId;
		this.categoryname = categoryname;
		this.medicineCount = medicineCount;
	}
	public CategoryDTO() {
		
	}
}
