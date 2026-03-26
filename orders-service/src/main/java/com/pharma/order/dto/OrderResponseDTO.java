package com.pharma.order.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pharma.order.entity.OrderItem;
import com.pharma.order.entity.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

public class OrderResponseDTO {
	private Long id;
	
	private Long customerId;
	
	private Long addressId;
	
	private double orderAmount;
	
	private List<OrderItem>items = new ArrayList<>();
	
	private LocalDateTime orderDate = LocalDateTime.now();

	private OrderStatus status ;

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

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderResponseDTO() {
		super();
	}
	
}
