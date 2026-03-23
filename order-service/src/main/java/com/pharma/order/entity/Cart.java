package com.pharma.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long cartId;
	
	@Column(nullable= false)
	private Long customerId;
	@Column(nullable= false)
	private Long medicineId;
	@Min(value=0,message = "quantity can't be negative")
	private int quantity;
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
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
	public Cart() {
		super();
	}
	public Cart(Long cartId, Long customerId, Long medicineId,int quantity) {
		super();
		this.cartId = cartId;
		this.customerId = customerId;
		this.medicineId = medicineId;
		this.quantity = quantity;
	}
	
}
