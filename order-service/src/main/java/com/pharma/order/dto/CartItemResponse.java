package com.pharma.order.dto;

import jakarta.validation.constraints.Min;

public class CartItemResponse {
	private Long cartId;
	private Long customerId;
    private Long medicineId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    
//    #-------- from medicine 
    private String medicineName;
    private double price;
    private double lineTotal;
    private boolean requiresPrescription;
    
//    Getters and setters
    
	public Long getCustomerId() {
		return customerId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
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
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	public boolean isRequiresPrescription() {
		return requiresPrescription;
	}
	public void setRequiresPrescription(boolean requiresPrescription) {
		this.requiresPrescription = requiresPrescription;
	}
    
    
}
