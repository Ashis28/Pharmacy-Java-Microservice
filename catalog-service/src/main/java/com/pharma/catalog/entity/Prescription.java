package com.pharma.catalog.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "prescriptions")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private Long customerId;

	@ElementCollection
    @CollectionTable(name = "prescription_medicine_ids", joinColumns = @JoinColumn(name = "prescription_id"))
    @Column(name = "medicine_id")
    private List<Long> medicineIds;
	
	private String notes;   // pharmacist's verification notes

    private LocalDateTime uploadedAt = LocalDateTime.now();
	
	
	public enum PrescriptionStatus {
        PENDING,    // just uploaded, awaiting review
        APPROVED,   // pharmacist approved - order can proceed
        REJECTED    // pharmacist rejected - customer must re-upload
    }
}
