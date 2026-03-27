package com.pharmacy.catalog.exception;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(Long id) {
        super("Medicine not found: " + id);
    }
}
