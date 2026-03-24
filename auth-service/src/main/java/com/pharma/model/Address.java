package com.pharma.model;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many addresses can belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String street;
    private String city;
    private String state;
    private Long pinCode;

    // marks the primary/default address for the user
    private boolean isDefault = false;

    public Address() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Long getPinCode() { return pinCode; }
    public void setPinCode(Long pinCode) { this.pinCode = pinCode; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
}
