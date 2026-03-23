package com.pharma.order.dto;

import jakarta.validation.constraints.Min;

public class CartItemRequest {
	private Long customerId;
    private Long medicineId;
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public CartItemRequest() {
		super();
	}
    
}
