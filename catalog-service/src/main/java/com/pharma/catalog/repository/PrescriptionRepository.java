package com.pharma.catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.catalog.entity.Prescription;
import com.pharma.catalog.entity.Prescription.PrescriptionStatus;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long>{
	 // All prescriptions uploaded by a specific customer
    List<Prescription> findByCustomerId(Long customerId);

    // Filter by status - admin uses this to see pending prescriptions queue
    List<Prescription> findByStatus(PrescriptionStatus status);

    // Customer's prescriptions filtered by status
    // e.g. findByCustomerIdAndStatus(42L, APPROVED) → approved prescriptions for customer 42
    List<Prescription> findByCustomerIdAndStatus(Long customerId, PrescriptionStatus status);

}
