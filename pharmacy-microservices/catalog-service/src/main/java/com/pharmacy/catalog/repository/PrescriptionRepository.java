package com.pharmacy.catalog.repository;

import com.pharmacy.catalog.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByStatus(Prescription.PrescriptionStatus status);
    List<Prescription> findByCustomerId(Long customerId);
}
