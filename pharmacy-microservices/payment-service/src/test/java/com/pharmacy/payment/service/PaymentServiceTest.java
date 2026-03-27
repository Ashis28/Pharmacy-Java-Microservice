package com.pharmacy.payment.service;

import com.pharmacy.payment.dto.PaymentRequest;
import com.pharmacy.payment.dto.PaymentResponse;
import com.pharmacy.payment.entity.Payment;
import com.pharmacy.payment.exception.PaymentAlreadyExistsException;
import com.pharmacy.payment.exception.PaymentNotFoundException;
import com.pharmacy.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock PaymentRepository paymentRepo;

    @InjectMocks PaymentService paymentService;

    private PaymentRequest request;
    private Payment successfulPayment;

    @BeforeEach
    void setUp() {
        request = new PaymentRequest();
        request.setOrderId(1L);
        request.setCustomerId(10L);
        request.setAmount(new BigDecimal("250.00"));
        request.setPaymentMethod("CARD");

        successfulPayment = new Payment();
        successfulPayment.setId(1L);
        successfulPayment.setOrderId(1L);
        successfulPayment.setCustomerId(10L);
        successfulPayment.setAmount(new BigDecimal("250.00"));
        successfulPayment.setStatus(Payment.PaymentStatus.SUCCESS);
        successfulPayment.setTransactionId("TXN-ABCD1234");
    }

    @Test
    void processPayment_success() {
        when(paymentRepo.findByOrderId(1L)).thenReturn(Collections.emptyList());
        when(paymentRepo.save(any(Payment.class))).thenReturn(successfulPayment);

        PaymentResponse response = paymentService.processPayment(request);

        assertThat(response.getOrderId()).isEqualTo(1L);
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        verify(paymentRepo).save(any(Payment.class));
    }

    @Test
    void processPayment_throwsPaymentAlreadyExistsException_whenAlreadyPaid() {
        when(paymentRepo.findByOrderId(1L)).thenReturn(List.of(successfulPayment));

        assertThatThrownBy(() -> paymentService.processPayment(request))
                .isInstanceOf(PaymentAlreadyExistsException.class)
                .hasMessageContaining("Payment already completed for order: 1");

        verify(paymentRepo, never()).save(any());
    }

    @Test
    void processPayment_allowsRetry_whenPreviousPaymentFailed() {
        Payment failedPayment = new Payment();
        failedPayment.setOrderId(1L);
        failedPayment.setStatus(Payment.PaymentStatus.FAILED);

        when(paymentRepo.findByOrderId(1L)).thenReturn(List.of(failedPayment));
        when(paymentRepo.save(any(Payment.class))).thenReturn(successfulPayment);

        PaymentResponse response = paymentService.processPayment(request);

        assertThat(response.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    void getByOrderId_success() {
        when(paymentRepo.findByOrderId(1L)).thenReturn(List.of(successfulPayment));

        PaymentResponse response = paymentService.getByOrderId(1L);

        assertThat(response.getOrderId()).isEqualTo(1L);
        assertThat(response.getTransactionId()).isEqualTo("TXN-ABCD1234");
    }

    @Test
    void getByOrderId_throwsPaymentNotFoundException_whenNotFound() {
        when(paymentRepo.findByOrderId(99L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> paymentService.getByOrderId(99L))
                .isInstanceOf(PaymentNotFoundException.class)
                .hasMessageContaining("Payment not found for order: 99");
    }
}
