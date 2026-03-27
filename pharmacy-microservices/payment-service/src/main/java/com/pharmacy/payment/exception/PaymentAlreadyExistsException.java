package com.pharmacy.payment.exception;

public class PaymentAlreadyExistsException extends RuntimeException {
    public PaymentAlreadyExistsException(Long orderId) {
        super("Payment already completed for order: " + orderId);
    }
}
