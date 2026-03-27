package com.pharmacy.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotNull private Long customerId;
    @NotEmpty private List<OrderItemRequest> items;
    private String deliveryAddress;

    @Data
    public static class OrderItemRequest {
        @NotNull private Long medicineId;
        @NotNull private Integer quantity;
    }
}
