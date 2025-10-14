package com.example.paymentservice.repository;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findPaymentById(int id);
    Optional<Payment> findPaymentByTuitionId(int tuitionId);
    Optional<Payment> findPaymentByTransactionId(int transactionId);
    @Query("SELECT a.balance FROM Account a WHERE a.userId = :userId")
    Double findBalanceByUserId(@Param("userId") int userId);
}
