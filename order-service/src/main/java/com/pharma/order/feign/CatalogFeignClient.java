package com.pharma.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pharma.order.dto.MedicineDTO;

@FeignClient(name = "catalog-service", url = "http://localhost:9097")
public interface CatalogFeignClient {

	@GetMapping("/api/catalog/medicines/{id}")
    MedicineDTO getMedicineById(@PathVariable("id") Long id);
	
	@PostMapping("/api/catalog/medicines/batch")
    List<MedicineDTO> getMedicinesByIds(@RequestBody List<Long> ids);
}
