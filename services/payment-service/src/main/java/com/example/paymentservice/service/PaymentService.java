package com.example.paymentservice.service;


import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.model.Account;
import com.example.paymentservice.model.Payment;

import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepo;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setUserId(request.getUserId());
        payment.setTuitionId(request.getTuitionId());
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setTransactionId(request.getTransactionId());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        Payment saved = paymentRepo.save(payment);

        return new PaymentResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getTuitionId(),
                saved.getAmount(),
                saved.getStatus().name(),
                saved.getCreatedAt()
        );
    }
    // public double getBalanceByUserId(int userId) {
    //     Double balance = paymentAccountRepo.findBalanceByUserId(userId);
    //     return balance != null ? balance : 0.0;
    // }

    public Optional<Payment> getPaymentById(int id) {
        return paymentRepo.findById(id);
    }
    
    public Optional<Payment> getPaymentByTuitionId(int tuitionId) {
        return paymentRepo.findPaymentByTuitionId(tuitionId);
    }
    
    public Optional<Payment> getPaymentByUserId(int userId) {
        return paymentRepo.findPaymentByUserId(userId);
    }
    public Double getBalanceByUserId(int userId) {
        return accountRepo.findBalanceByUserId(userId);
    }
    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }
    @Transactional
    public Payment updatePaymentStatus(int id, PaymentStatus status) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());
        return paymentRepo.save(payment);
    }
    @Transactional
    public void updateBalanceByUserId(int userId, Double updBalance) {
        // Kiểm tra Account có tồn tại không
        Account account = accountRepo.findAccountByUserId(userId);
        if (account == null) {
            throw new RuntimeException("Account not found for user ID: " + userId);
        }
        
        // Sử dụng @Modifying query để update trực tiếp database
        int updatedRows = accountRepo.updateBalanceByUserId(userId, updBalance);
        if (updatedRows == 0) {
            throw new RuntimeException("Failed to update balance for user ID: " + userId);
        }
    }
    @Transactional
    public void deletePayment(int id) {
        paymentRepo.deleteById(id);
    }
}
