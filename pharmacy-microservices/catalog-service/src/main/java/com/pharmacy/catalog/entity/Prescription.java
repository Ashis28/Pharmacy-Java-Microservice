package com.pharmacy.catalog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Data
public class Prescription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    private String customerEmail;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status = PrescriptionStatus.PENDING;

    private String rejectionReason;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    public enum PrescriptionStatus { PENDING, APPROVED, REJECTED }
}
