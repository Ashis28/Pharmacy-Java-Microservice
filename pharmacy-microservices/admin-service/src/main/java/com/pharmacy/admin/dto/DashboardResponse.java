package com.pharmacy.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DashboardResponse {
    private long totalOrders;
    private long pendingPrescriptions;
    private long lowStockCount;
    private BigDecimal monthlyRevenue;
}
