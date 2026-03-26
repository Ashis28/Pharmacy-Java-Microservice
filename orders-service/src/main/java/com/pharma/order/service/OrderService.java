package com.pharma.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.order.dto.CartDTO;
import com.pharma.order.dto.CheckoutRequest;
import com.pharma.order.dto.OrderResponseDTO;
import com.pharma.order.entity.Order;
import com.pharma.order.entity.OrderItem;
import com.pharma.order.entity.OrderStatus;
import com.pharma.order.feign.CartFeignClient;
import com.pharma.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CartFeignClient cartFeignClient;
//	1.checkout
	public OrderResponseDTO checkout(CheckoutRequest request) {
		
		List<CartDTO> cartItems = cartFeignClient.getCartsByCustomerId(request.getCustomerId());
		
		Order order = new Order();
		order.setCustomerId(request.getCustomerId());
		order.setAddressId(request.getAddressId());
	
		double total = 0.0;
		for(CartDTO cartItem:cartItems) {
			OrderItem item = new OrderItem();
			item.setMedicineId(cartItem.getMedicineId());
			item.setMedicineName(cartItem.getMedicineName());
			item.setQuantity(cartItem.getQuantity());
			item.setUnitPrice(cartItem.getQuantity()*cartItem.getPrice());
			item.setOrder(order);
			order.getItems().add(item);
			total += item.getUnitPrice();
		}
		
		order.setOrderAmount(total);
		orderRepository.save(order);
		
//		cartFeignClient.deleteCartByCustomerId(request.getCustomerId());
		
		return toDTO(order);
	}
	public List<Order> getAllOrdersById(Long id){
		return orderRepository.findByCustomerId(id);
	}
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).get();
	}
	public List<Order> getAllOrdersByOrderIdAndCustomerId(Long id,Long customerId){
		return orderRepository.findByOrderIdAndCustomerId(id, customerId);
	}
	public OrderResponseDTO cancelOrder(Long id) {
		Order order = getOrderById(id);
		if(order!=null) {
			order.setStatus(OrderStatus.CANCELLED);
			orderRepository.save(order);
		}
		return toDTO(order);
	}
	
	public String saveOrder(Order order) {
		orderRepository.save(order);
		return "Saved";
	}
	
	private OrderResponseDTO toDTO(Order order) {
		OrderResponseDTO ord = new OrderResponseDTO();
		ord.setCustomerId(order.getCustomerId());
		ord.setAddressId(order.getAddressId());
		ord.setId(order.getId());
		ord.setItems(order.getItems());
		ord.setOrderAmount(order.getOrderAmount());
		ord.setOrderDate(ord.getOrderDate());
		ord.setStatus(OrderStatus.PACKED);
		
		return ord;
	}
}
