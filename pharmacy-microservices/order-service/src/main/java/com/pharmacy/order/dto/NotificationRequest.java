package com.pharmacy.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequest {
    private String recipientEmail;
    private String subject;
    private String message;
    private String type;
}
