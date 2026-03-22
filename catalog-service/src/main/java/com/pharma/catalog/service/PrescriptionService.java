package com.pharma.catalog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.catalog.dto.PrescriptionDTO;
import com.pharma.catalog.entity.Prescription;
import com.pharma.catalog.entity.Prescription.PrescriptionStatus;
import com.pharma.catalog.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class PrescriptionService {

	@Autowired
	PrescriptionRepository prescriptionRepository;
	
	public PrescriptionDTO getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        return toDTO(prescription);
    }

    public List<PrescriptionDTO> getPrescriptionsByCustomer(Long customerId) {
        return prescriptionRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PrescriptionDTO> getPrescriptionsByStatus(PrescriptionStatus status) {
        return prescriptionRepository.findByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /*
     * File upload handled separately (in controller via MultipartFile).
     * This method saves the prescription record AFTER the file has been stored.
     * fileUrl = path where the file was saved (local disk or S3 URL).
     */
    @Transactional
    public PrescriptionDTO createPrescription(Long customerId, List<Long> medicineIds) {
        Prescription prescription = new Prescription();
        prescription.setCustomerId(customerId);
        prescription.setMedicineIds(medicineIds);
        prescription.setStatus(PrescriptionStatus.PENDING);
        prescription.setUploadedAt(LocalDateTime.now());
        return toDTO(prescriptionRepository.save(prescription));
    }

    /*
     * Admin/Pharmacist reviews the prescription.
     * reviewerId = the admin's userId (passed in request, from their session/token).
     */
    @Transactional
    public PrescriptionDTO reviewPrescription(Long id, PrescriptionStatus newStatus,
                                              String notes, Long reviewerId) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));

        if (prescription.getStatus() != PrescriptionStatus.PENDING) {
            throw new RuntimeException("Prescription already reviewed. Current status: " + prescription.getStatus());
        }

        prescription.setStatus(newStatus);
        prescription.setNotes(notes);
        return toDTO(prescriptionRepository.save(prescription));
    }
	
	private PrescriptionDTO toDTO(Prescription p) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(p.getId());
        dto.setCustomerId(p.getCustomerId());
        dto.setMedicineIds(p.getMedicineIds());
        dto.setStatus(p.getStatus());
        dto.setNotes(p.getNotes());
        dto.setUploadedAt(p.getUploadedAt());
        return dto;
	}
	
}
