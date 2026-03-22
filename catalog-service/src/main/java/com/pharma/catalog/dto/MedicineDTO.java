package com.pharma.catalog.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicineDTO {

    private Long id;

    @NotBlank(message = "Medicine name is required")
    private String name;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    private boolean requiresPrescription;

    private String dosageInfo;

    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private String categoryName;

    // No-args constructor
    public MedicineDTO() {
    }

    // All-args constructor
    public MedicineDTO(Long id, String name, String manufacturer, double price, int stock,
                       boolean requiresPrescription, String dosageInfo, LocalDate expiryDate,
                       Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.stock = stock;
        this.requiresPrescription = requiresPrescription;
        this.dosageInfo = dosageInfo;
        this.expiryDate = expiryDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }

    public void setRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }

    public String getDosageInfo() {
        return dosageInfo;
    }

    public void setDosageInfo(String dosageInfo) {
        this.dosageInfo = dosageInfo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Optional: toString()

    @Override
    public String toString() {
        return "MedicineDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", requiresPrescription=" + requiresPrescription +
                ", dosageInfo='" + dosageInfo + '\'' +
                ", expiryDate=" + expiryDate +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}