package com.pharmacy.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicineDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean requiresPrescription;
}
