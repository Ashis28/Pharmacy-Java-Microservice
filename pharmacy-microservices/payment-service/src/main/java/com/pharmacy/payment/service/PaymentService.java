package com.pharmacy.payment.service;

import com.pharmacy.payment.dto.PaymentRequest;
import com.pharmacy.payment.dto.PaymentResponse;
import com.pharmacy.payment.entity.Payment;
import com.pharmacy.payment.exception.PaymentAlreadyExistsException;
import com.pharmacy.payment.exception.PaymentNotFoundException;
import com.pharmacy.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;

    public PaymentResponse processPayment(PaymentRequest req) {
        // Prevent duplicate payment for the same order
        boolean alreadyPaid = paymentRepo.findByOrderId(req.getOrderId()).stream()
                .anyMatch(p -> p.getStatus() == Payment.PaymentStatus.SUCCESS);
        if (alreadyPaid) {
            throw new PaymentAlreadyExistsException(req.getOrderId());
        }

        Payment payment = new Payment();
        payment.setOrderId(req.getOrderId());
        payment.setCustomerId(req.getCustomerId());
        payment.setAmount(req.getAmount());
        payment.setPaymentMethod(req.getPaymentMethod());
        // Simulate payment success (integrate real gateway here)
        payment.setStatus(Payment.PaymentStatus.SUCCESS);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        Payment saved = paymentRepo.save(payment);
        return toResponse(saved);
    }

    public PaymentResponse getByOrderId(Long orderId) {
        return paymentRepo.findByOrderId(orderId).stream()
                .findFirst().map(this::toResponse)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
    }

    private PaymentResponse toResponse(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.setPaymentId(p.getId());
        r.setOrderId(p.getOrderId());
        r.setAmount(p.getAmount());
        r.setStatus(p.getStatus().name());
        r.setTransactionId(p.getTransactionId());
        r.setCreatedAt(p.getCreatedAt());
        return r;
    }
}
