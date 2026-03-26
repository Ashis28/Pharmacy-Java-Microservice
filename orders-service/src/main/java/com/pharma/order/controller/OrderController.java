package com.pharma.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.order.dto.CheckoutRequest;
import com.pharma.order.dto.OrderResponseDTO;
import com.pharma.order.entity.Order;
import com.pharma.order.service.OrderService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@PostMapping("/checkout")
	public ResponseEntity<OrderResponseDTO> addOrders(@RequestBody CheckoutRequest request) {
		return ResponseEntity.ok(orderService.checkout (request));
	}
	@GetMapping("/get/{id}")
	public Order getOrderById(@PathVariable int id) {
		return orderService.getOrderById(null);
	}
	@RequestMapping("/getByCustomer/{id}")
	public ResponseEntity<List<Order>>getOrdersByCustomerId(@PathVariable Long id){
		return ResponseEntity.ok(orderService.getAllOrdersById(id));
	}
	@RequestMapping("/cancel/{id}")
	public ResponseEntity<OrderResponseDTO> cancelOrderByOrderId(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.cancelOrder(id));
	}
}
