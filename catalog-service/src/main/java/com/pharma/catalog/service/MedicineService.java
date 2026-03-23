package com.pharma.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pharma.catalog.dto.MedicineDTO;
import com.pharma.catalog.entity.Category;
import com.pharma.catalog.entity.Medicine;
import com.pharma.catalog.repository.CategoryRepository;
import com.pharma.catalog.repository.MedicineRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicineService {
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public List<MedicineDTO>getAllMedicines(){
		return medicineRepository.findAllInStockWithCategory()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
	}
	public MedicineDTO getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found with id: " + id));
        System.out.println("returned entity"+medicine.getManufacturer());
        return toDTO(medicine);
    }
	public List<MedicineDTO> searchByName(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MedicineDTO> getMedicinesByCategory(Long categoryId) {
        return medicineRepository.findByCategoryCategoryId(categoryId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public List<MedicineDTO> getMedicinesByIds(List<Long> ids) {
        return medicineRepository.findAllByIdIn(ids)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MedicineDTO createMedicine(MedicineDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
        Medicine medicine = toEntity(dto, category);
        return toDTO(medicineRepository.save(medicine));
    }
    @Transactional
    public MedicineDTO updateMedicine(Long id, MedicineDTO dto) {
        Medicine existing = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));

        existing.setName(dto.getName());
        existing.setManufacturer(dto.getManufacturer());
        existing.setPrice(dto.getPrice());
        existing.setStock(dto.getStock());
        existing.setRequiresPrescription(dto.isRequiresPrescription());
        existing.setExpiryDate(dto.getExpiryDate());
        existing.setCategory(category);

        return toDTO(medicineRepository.save(existing));
    }

    /*
     * Stock deduction - called internally when an order is placed.
     * The Order Service calls PATCH /api/catalog/medicines/{id}/stock?quantity=3
     * via Feign to reduce stock after a successful payment.
     */
    @Transactional
    public void deductStock(Long medicineId, int quantity) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found: " + medicineId));
        if (medicine.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for medicine: " + medicine.getName());
        }
        medicine.setStock(medicine.getStock() - quantity);
        medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new RuntimeException("Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }
	
//    #---------------- Mapping Helpers --------------------------
	private MedicineDTO toDTO(Medicine m) {
        MedicineDTO dto = new MedicineDTO();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setManufacturer(m.getManufacturer());
        dto.setPrice(m.getPrice());
        dto.setStock(m.getStock());
        dto.setRequiresPrescription(m.isRequiresPrescription());
        dto.setExpiryDate(m.getExpiryDate());
        dto.setCategoryId(m.getCategory().getCategoryId());
        dto.setCategoryName(m.getCategory().getCatName());
        return dto;
    }
	private Medicine toEntity(MedicineDTO dto, Category category) {
        Medicine m = new Medicine();
        m.setName(dto.getName());
        m.setManufacturer(dto.getManufacturer());
        m.setPrice(dto.getPrice());
        m.setStock(dto.getStock());
        m.setRequiresPrescription(dto.isRequiresPrescription());
        m.setDosageInfo(dto.getDosageInfo());
        m.setExpiryDate(dto.getExpiryDate());
        m.setCategory(category);
       
        return m;
    }
}
