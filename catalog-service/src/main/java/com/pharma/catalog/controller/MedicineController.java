package com.pharma.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.catalog.dto.MedicineDTO;
import com.pharma.catalog.service.MedicineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalog/medicines")
public class MedicineController {

	@Autowired
	MedicineService medicineService;
	
	@GetMapping
	public ResponseEntity<List<MedicineDTO>>getMedicines(
		@RequestParam(required = false) String name,
		@RequestParam(required = false) Long categoryId){
		if (name != null && !name.isBlank()) {
	            return ResponseEntity.ok(medicineService.searchByName(name));
	        }
        if (categoryId != null) {
            return ResponseEntity.ok(medicineService.getMedicinesByCategory(categoryId));
        }
        return ResponseEntity.ok(medicineService.getAllMedicines());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicineDTO>getMedicineById(@PathVariable Long Id){
		return ResponseEntity.ok(medicineService.getMedicineById(Id));
	}
	@PostMapping("/batch")
    public ResponseEntity<List<MedicineDTO>> getMedicinesByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(medicineService.getMedicinesByIds(ids));
    }
   @PostMapping
    public ResponseEntity<MedicineDTO> createMedicine(@Valid @RequestBody MedicineDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.createMedicine(dto));
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<MedicineDTO> updateMedicine(
			@PathVariable Long id,
			@Valid @RequestBody MedicineDTO dto){
		return ResponseEntity.ok(medicineService.updateMedicine(id, dto));
	}
	@PatchMapping("/{id}/stock")
    public ResponseEntity<Void> deductStock(@PathVariable Long id,
                                             @RequestParam int quantity) {
        medicineService.deductStock(id, quantity);
        return ResponseEntity.ok().build();
    }
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
