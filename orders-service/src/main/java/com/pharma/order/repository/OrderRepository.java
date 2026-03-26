package com.pharma.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.order.entity.Order;
import com.pharma.order.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order,Long>{

	List<Order>findByCustomerId(Long customerId);
	List<Order>findByOrderIdAndCustomerId(Long orderId,Long customerId);
	
	//find order by status
	List<Order> findOrderByStatus(OrderStatus status);
}
