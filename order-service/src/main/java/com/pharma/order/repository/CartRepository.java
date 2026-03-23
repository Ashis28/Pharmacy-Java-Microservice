package com.pharma.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.order.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{
	List<Cart> findByCustomerId(Long customerId);

    // Find a specific medicine in a customer's cart (for quantity update)
    Optional<Cart> findByCustomerIdAndMedicineId(Long customerId, Long medicineId);
    void deleteByCustomerIdAndMedicineId(Long customerId, Long medicineId);

    // Clear entire cart after order is placed
    void deleteByCustomerId(Long customerId);
}
