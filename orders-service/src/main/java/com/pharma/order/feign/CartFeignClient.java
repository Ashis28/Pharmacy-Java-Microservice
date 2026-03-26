package com.pharma.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pharma.order.dto.CartDTO;

@FeignClient(name="order-service",url = "http://localhost:9098")
public interface CartFeignClient {
	@GetMapping("/api/cart/{customerId}")
    List<CartDTO> getCartsByCustomerId(@PathVariable("customerId") Long id);
	
	@DeleteMapping("/api/cart/{customerId}")
	CartDTO deleteCartByCustomerId(@PathVariable("customerId") Long id);
	
}
