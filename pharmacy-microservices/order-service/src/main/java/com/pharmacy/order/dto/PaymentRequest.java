package com.pharmacy.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private Long customerId;
    private BigDecimal amount;
    private String paymentMethod; // CARD, UPI, COD
}
