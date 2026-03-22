package com.pharma.catalog.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharma.catalog.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine,Long>{


    // Search by name - case insensitive partial match
    // SQL: SELECT * FROM medicines WHERE LOWER(name) LIKE LOWER('%keyword%')
    List<Medicine> findByNameContainingIgnoreCase(String name);

    // All medicines in a category
    // SQL: SELECT * FROM medicines WHERE category_id = ?
    List<Medicine> findByCategoryCategoryId(Long categoryId);

    // Only medicines that don't need a prescription (for browse without login)
    List<Medicine> findByRequiresPrescriptionFalse();

    // Expiry check - medicines expiring before a given date
    List<Medicine> findByExpiryDateBefore(LocalDate date);

    // Low stock alert - for admin dashboard
    List<Medicine> findByStockLessThan(int threshold);

    // Search by name AND category - for filtered listing page
    List<Medicine> findByNameContainingIgnoreCaseAndCategoryCategoryId(String name, Long categoryId);

    /*
     * Custom JPQL query - useful when derived method names get too long.
     * This fetches medicine + its category in one JOIN query,
     * avoiding the N+1 problem (where JPA fires a separate query per medicine to load category).
     */
    @Query("SELECT m FROM Medicine m JOIN FETCH m.category WHERE m.stock > 0")
    List<Medicine> findAllInStockWithCategory();

    // Used by Order Service via Feign: fetch multiple medicines by their IDs at once
    // SQL: SELECT * FROM medicines WHERE id IN (?, ?, ?)
    @Query("SELECT m FROM Medicine m WHERE m.id IN :ids")
    List<Medicine> findAllByIdIn(@Param("ids") List<Long> ids);
}
