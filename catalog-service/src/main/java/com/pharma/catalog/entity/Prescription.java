package com.pharma.catalog.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private PrescriptionStatus status = PrescriptionStatus.PENDING;

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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
	public PrescriptionStatus getStatus() {
		return status;
	}


	public void setStatus(PrescriptionStatus status) {
		this.status = status;
	}


	public List<Long> getMedicineIds() {
		return medicineIds;
	}


	public void setMedicineIds(List<Long> medicineIds) {
		this.medicineIds = medicineIds;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}


	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	
	
}
