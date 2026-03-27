package com.pharmacy.order.service;

import com.pharmacy.order.client.CatalogClient;
import com.pharmacy.order.client.PaymentClient;
import com.pharmacy.order.dto.MedicineDto;
import com.pharmacy.order.dto.OrderRequest;
import com.pharmacy.order.dto.OrderResponse;
import com.pharmacy.order.entity.Order;
import com.pharmacy.order.entity.OrderItem;
import com.pharmacy.order.exception.InvalidOrderStatusException;
import com.pharmacy.order.exception.OrderNotFoundException;
import com.pharmacy.order.messaging.NotificationPublisher;
import com.pharmacy.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock OrderRepository orderRepo;
    @Mock CatalogClient catalogClient;
    @Mock PaymentClient paymentClient;
    @Mock NotificationPublisher notificationPublisher;

    @InjectMocks OrderService orderService;

    private Order savedOrder;

    @BeforeEach
    void setUp() {
        savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerId(10L);
        savedOrder.setCustomerEmail("customer@example.com");
        savedOrder.setStatus(Order.OrderStatus.PENDING);
        savedOrder.setTotalAmount(new BigDecimal("100.00"));
        savedOrder.setDeliveryAddress("123 Main St");
        savedOrder.setItems(new ArrayList<>());
    }

    @Test
    void placeOrder_success() {
        OrderRequest req = new OrderRequest();
        req.setCustomerId(10L);
        req.setDeliveryAddress("123 Main St");

        OrderRequest.OrderItemRequest itemReq = new OrderRequest.OrderItemRequest();
        itemReq.setMedicineId(1L);
        itemReq.setQuantity(2);
        req.setItems(List.of(itemReq));

        MedicineDto medicine = new MedicineDto();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setPrice(new BigDecimal("50.00"));

        when(catalogClient.getMedicineById(1L)).thenReturn(medicine);
        when(orderRepo.save(any(Order.class))).thenReturn(savedOrder);
        doNothing().when(notificationPublisher).publish(any());

        OrderResponse response = orderService.placeOrder(req, "customer@example.com");

        assertThat(response.getCustomerId()).isEqualTo(10L);
        assertThat(response.getStatus()).isEqualTo("PENDING");
        verify(orderRepo).save(any(Order.class));
    }

    @Test
    void getById_success() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(savedOrder));

        OrderResponse response = orderService.getById(1L);

        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void getById_throwsOrderNotFoundException_whenNotFound() {
        when(orderRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getById(99L))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessageContaining("Order not found: 99");
    }

    @Test
    void updateStatus_success() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(savedOrder));
        when(orderRepo.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponse response = orderService.updateStatus(1L, "PAID");

        verify(orderRepo).save(savedOrder);
    }

    @Test
    void updateStatus_throwsInvalidOrderStatusException_whenStatusInvalid() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(savedOrder));

        assertThatThrownBy(() -> orderService.updateStatus(1L, "FLYING"))
                .isInstanceOf(InvalidOrderStatusException.class)
                .hasMessageContaining("Invalid order status: FLYING");
    }

    @Test
    void getByCustomer_returnsOrders() {
        when(orderRepo.findByCustomerId(10L)).thenReturn(List.of(savedOrder));

        List<OrderResponse> orders = orderService.getByCustomer(10L);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getCustomerId()).isEqualTo(10L);
    }
}
