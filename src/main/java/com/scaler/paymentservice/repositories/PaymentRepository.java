package com.scaler.paymentservice.repositories;

import com.scaler.paymentservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByPaymentGatewayReferenceId(String paymentGatewayReferenceId);
}
