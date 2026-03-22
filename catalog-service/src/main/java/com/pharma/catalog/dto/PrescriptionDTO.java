package com.pharma.catalog.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.pharma.catalog.entity.Prescription.PrescriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PrescriptionDTO {

    private Long id;

    private Long customerId;

    private List<Long> medicineIds;

    /*
     * This is the field the Order Service checks via Feign:
     *   GET /api/catalog/prescriptions/{id}
     *   → deserialize PrescriptionDTO
     *   → check dto.getStatus() == APPROVED
     */
    private PrescriptionStatus status;

    private String notes;

    private LocalDateTime uploadedAt;
    public PrescriptionDTO(){
    	
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
	public List<Long> getMedicineIds() {
		return medicineIds;
	}
	public void setMedicineIds(List<Long> medicineIds) {
		this.medicineIds = medicineIds;
	}
	public PrescriptionStatus getStatus() {
		return status;
	}
	public void setStatus(PrescriptionStatus status) {
		this.status = status;
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
