package com.example.transactionservice.repository;

import com.example.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
        Optional<Transaction> findById(int id);
        Optional<Transaction> findByUserId(int userId);
        Optional<Transaction> findByTuitionId(int tuitionId);
        Optional<Transaction> findByStudentId(String studentId);

}
