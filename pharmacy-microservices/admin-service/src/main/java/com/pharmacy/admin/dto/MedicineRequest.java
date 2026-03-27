package com.pharmacy.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MedicineRequest {
    @NotBlank private String name;
    private String description;
    @NotNull @Min(0) private BigDecimal price;
    @NotNull @Min(0) private Integer stockQuantity;
    private boolean requiresPrescription;
    private LocalDate expiryDate;
    private Long categoryId;
}
