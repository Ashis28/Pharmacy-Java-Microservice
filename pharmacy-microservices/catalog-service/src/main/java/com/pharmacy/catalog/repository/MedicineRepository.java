package com.pharmacy.catalog.repository;

import com.pharmacy.catalog.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);
    List<Medicine> findByCategoryId(Long categoryId);

    @Query("SELECT COUNT(m) FROM Medicine m WHERE m.stockQuantity <= 10")
    long countLowStock();

    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity <= 10")
    List<Medicine> findLowStockMedicines();
}
