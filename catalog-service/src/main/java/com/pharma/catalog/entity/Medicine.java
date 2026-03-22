package com.pharma.catalog.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="medicines")

public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "medicine name can't be null")
	@Column(nullable=false)
	private String name;
	
	@NotBlank(message = "manufacturer can't be null")
	private String manufacturer;
	
	@Min(value=0 , message = "price can't be negative")
	@Column(nullable = false)
	private double price;
	
	@Min(value = 0, message = "Stock cannot be negative")
	private int stock;
	
	@Column(nullable = false)
	private boolean requiresPrescription;
	
	@Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "category_id",nullable = false)
	private Category category;
}
