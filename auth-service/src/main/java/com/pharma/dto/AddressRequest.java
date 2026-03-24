package com.pharma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressRequest {

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotNull(message = "Pin code is required")
    private Long pinCode;

    public AddressRequest() {}

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Long getPinCode() { return pinCode; }
    public void setPinCode(Long pinCode) { this.pinCode = pinCode; }
}
