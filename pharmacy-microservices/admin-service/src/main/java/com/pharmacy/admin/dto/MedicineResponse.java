package com.pharmacy.admin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MedicineResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean requiresPrescription;
    private LocalDate expiryDate;
    private String categoryName;
}
