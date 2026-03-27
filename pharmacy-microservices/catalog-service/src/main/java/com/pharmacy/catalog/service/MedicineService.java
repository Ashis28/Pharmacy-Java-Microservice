package com.pharmacy.catalog.service;

import com.pharmacy.catalog.dto.MedicineRequest;
import com.pharmacy.catalog.dto.MedicineResponse;
import com.pharmacy.catalog.entity.Category;
import com.pharmacy.catalog.entity.Medicine;
import com.pharmacy.catalog.exception.CategoryNotFoundException;
import com.pharmacy.catalog.exception.MedicineNotFoundException;
import com.pharmacy.catalog.repository.CategoryRepository;
import com.pharmacy.catalog.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepo;
    private final CategoryRepository categoryRepo;

    public List<MedicineResponse> getAll() {
        return medicineRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public MedicineResponse getById(Long id) {
        return toResponse(medicineRepo.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException(id)));
    }

    public List<MedicineResponse> search(String name) {
        return medicineRepo.findByNameContainingIgnoreCase(name).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    public MedicineResponse create(MedicineRequest req) {
        Medicine m = new Medicine();
        mapFields(m, req);
        return toResponse(medicineRepo.save(m));
    }

    public MedicineResponse update(Long id, MedicineRequest req) {
        Medicine m = medicineRepo.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));
        mapFields(m, req);
        return toResponse(medicineRepo.save(m));
    }

    public void delete(Long id) {
        medicineRepo.deleteById(id);
    }

    public long getLowStockCount() {
        return medicineRepo.countLowStock();
    }

    public List<MedicineResponse> getLowStockMedicines() {
        return medicineRepo.findLowStockMedicines().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    private void mapFields(Medicine m, MedicineRequest req) {
        m.setName(req.getName());
        m.setDescription(req.getDescription());
        m.setPrice(req.getPrice());
        m.setStockQuantity(req.getStockQuantity());
        m.setRequiresPrescription(req.isRequiresPrescription());
        m.setExpiryDate(req.getExpiryDate());
        if (req.getCategoryId() != null) {
            Category cat = categoryRepo.findById(req.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(req.getCategoryId()));
            m.setCategory(cat);
        }
    }

    private MedicineResponse toResponse(Medicine m) {
        MedicineResponse r = new MedicineResponse();
        r.setId(m.getId());
        r.setName(m.getName());
        r.setDescription(m.getDescription());
        r.setPrice(m.getPrice());
        r.setStockQuantity(m.getStockQuantity());
        r.setRequiresPrescription(m.isRequiresPrescription());
        r.setExpiryDate(m.getExpiryDate());
        if (m.getCategory() != null) r.setCategoryName(m.getCategory().getName());
        return r;
    }
}
