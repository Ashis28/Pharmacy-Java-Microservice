package com.pharmacy.admin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String customerEmail;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
