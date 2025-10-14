package com.example.paymentservice.repository;

import com.example.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findPaymentById(int id);
    Optional<Payment> findPaymentByTuitionId(int tuitionId);
    Optional<Payment> findPaymentByTransactionId(int transactionId);
}
