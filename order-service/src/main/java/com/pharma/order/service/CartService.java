package com.pharma.order.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.order.dto.CartItemRequest;
import com.pharma.order.dto.CartItemResponse;
import com.pharma.order.dto.MedicineDTO;
import com.pharma.order.entity.Cart;
import com.pharma.order.feign.CatalogFeignClient;
import com.pharma.order.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CatalogFeignClient  catalogFeignClient ;
	
	@Transactional
	public CartItemResponse addToCart(CartItemRequest request) {
		
		MedicineDTO medicine = catalogFeignClient.getMedicineById(request.getMedicineId());
		
		if (medicine.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for: " + medicine.getName()
                    + ". Available: " + medicine.getStock());
        }
		Optional<Cart> existing = cartRepository.findByCustomerIdAndMedicineId(request.getCustomerId(), request.getMedicineId());
		
		//if cart already exist then just update ??
		Cart cart;
		if(existing.isPresent()) {
			cart = existing.get();
			cart.setQuantity(request.getQuantity());
		}
		else {
			cart = new Cart();
			cart.setCustomerId(request.getCustomerId());
			cart.setMedicineId(request.getMedicineId());
			cart.setQuantity(request.getQuantity());
		}
		cartRepository.save(cart);
		
		return toResponse(cart,medicine);
	}
//	Get All the Items present in the cart
	 public List<CartItemResponse> getCart(Long customerId) {
	        List<Cart> cartItems = cartRepository.findByCustomerId(customerId);
	        if (cartItems.isEmpty()) {
	            return List.of();
	        }
	 
	        List<Long> medicineIds = cartItems.stream()
	                .map(Cart::getMedicineId)
	                .collect(Collectors.toList());
	 
	        Map<Long, MedicineDTO> medicineMap = catalogFeignClient
	                .getMedicinesByIds(medicineIds)
	                .stream()
	                .collect(Collectors.toMap(MedicineDTO::getId, m -> m));
	 
	        return cartItems.stream()
	                .map(cart -> toResponse(cart, medicineMap.get(cart.getMedicineId())))
	                .collect(Collectors.toList());
	    }
	 
	@Transactional
    public void removeItem(Long customerId, Long medicineId) {
        cartRepository.deleteByCustomerIdAndMedicineId(customerId, medicineId);
    }
 
    @Transactional
    public void clearCart(Long customerId) {
        cartRepository.deleteByCustomerId(customerId);
    }
	
	public CartItemResponse toResponse(Cart cart,MedicineDTO medicine) {
		CartItemResponse response = new CartItemResponse();
        response.setCartId(cart.getCartId());
        response.setMedicineId(cart.getMedicineId());
        response.setQuantity(cart.getQuantity());
 
        if (medicine != null) {
            response.setMedicineName(medicine.getName());
            response.setPrice(medicine.getPrice());
            response.setLineTotal(medicine.getPrice() * cart.getQuantity());
            response.setRequiresPrescription(medicine.isRequiresPrescription());
        }
        return response;
	}
}
