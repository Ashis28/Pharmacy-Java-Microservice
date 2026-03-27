package com.pharmacy.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrescriptionResponse {
    private Long id;
    private Long customerId;
    private String customerEmail;
    private String imageUrl;
    private String status;
    private String rejectionReason;
    private LocalDateTime uploadedAt;
}
