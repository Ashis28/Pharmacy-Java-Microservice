package com.pharmacy.catalog.service;

import com.pharmacy.catalog.dto.MedicineRequest;
import com.pharmacy.catalog.dto.MedicineResponse;
import com.pharmacy.catalog.entity.Category;
import com.pharmacy.catalog.entity.Medicine;
import com.pharmacy.catalog.exception.CategoryNotFoundException;
import com.pharmacy.catalog.exception.MedicineNotFoundException;
import com.pharmacy.catalog.repository.CategoryRepository;
import com.pharmacy.catalog.repository.MedicineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock MedicineRepository medicineRepo;
    @Mock CategoryRepository categoryRepo;

    @InjectMocks MedicineService medicineService;

    private Medicine medicine;
    private MedicineRequest request;

    @BeforeEach
    void setUp() {
        medicine = new Medicine();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setPrice(new BigDecimal("50.00"));
        medicine.setStockQuantity(100);
        medicine.setRequiresPrescription(false);

        request = new MedicineRequest();
        request.setName("Paracetamol");
        request.setPrice(new BigDecimal("50.00"));
        request.setStockQuantity(100);
        request.setRequiresPrescription(false);
    }

    @Test
    void getAll_returnsList() {
        when(medicineRepo.findAll()).thenReturn(List.of(medicine));

        List<MedicineResponse> result = medicineService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Paracetamol");
    }

    @Test
    void getById_success() {
        when(medicineRepo.findById(1L)).thenReturn(Optional.of(medicine));

        MedicineResponse response = medicineService.getById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Paracetamol");
    }

    @Test
    void getById_throwsMedicineNotFoundException_whenNotFound() {
        when(medicineRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> medicineService.getById(99L))
                .isInstanceOf(MedicineNotFoundException.class)
                .hasMessageContaining("Medicine not found: 99");
    }

    @Test
    void create_success() {
        when(medicineRepo.save(any(Medicine.class))).thenReturn(medicine);

        MedicineResponse response = medicineService.create(request);

        assertThat(response.getName()).isEqualTo("Paracetamol");
        verify(medicineRepo).save(any(Medicine.class));
    }

    @Test
    void create_throwsCategoryNotFoundException_whenCategoryInvalid() {
        request.setCategoryId(99L);
        when(categoryRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> medicineService.create(request))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found: 99");
    }

    @Test
    void update_success() {
        when(medicineRepo.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineRepo.save(any(Medicine.class))).thenReturn(medicine);

        MedicineResponse response = medicineService.update(1L, request);

        assertThat(response.getName()).isEqualTo("Paracetamol");
    }

    @Test
    void update_throwsMedicineNotFoundException_whenNotFound() {
        when(medicineRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> medicineService.update(99L, request))
                .isInstanceOf(MedicineNotFoundException.class);
    }

    @Test
    void delete_callsRepository() {
        doNothing().when(medicineRepo).deleteById(1L);

        medicineService.delete(1L);

        verify(medicineRepo).deleteById(1L);
    }
}
