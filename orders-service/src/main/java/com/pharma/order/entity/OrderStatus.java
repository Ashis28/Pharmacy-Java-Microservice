package com.pharma.order.entity;
public enum OrderStatus {
    PENDING,            // order created, awaiting payment
    CONFIRMED,          // payment received
    PACKED,             // being prepared
    OUT_FOR_DELIVERY,   // with delivery agent
    DELIVERED,          // completed
    CANCELLED           // cancelled by customer or admin
}