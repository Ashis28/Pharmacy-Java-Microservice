package com.pharmacy.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long orderId;
    private Long customerId;
    private BigDecimal amount;
    private String paymentMethod;
}
