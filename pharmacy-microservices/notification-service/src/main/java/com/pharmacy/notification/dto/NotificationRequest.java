package com.pharmacy.notification.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private String recipientEmail;
    private String subject;
    private String message;
    private String type; // ORDER_PLACED, PAYMENT_SUCCESS, PRESCRIPTION_APPROVED, etc.
}
