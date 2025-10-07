package com.example.transactionservice.service;


import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction getTransactionById(int transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            return transaction.get();
        }
        else {
            throw new RuntimeException("Transaction (ID:"+transactionId+") not found");
        }
    }
    public Transaction getTransactionByTuitionId(int tuitionId) {
        Optional<Transaction> transaction = transactionRepository.findByTuitionId(tuitionId);
        if(transaction.isPresent()){
            return transaction.get();
        }
        else {
            throw new RuntimeException("Tuition (ID:"+tuitionId+") not found");
        }
    }
    public Transaction getTransactionByUserId(int userId) {
        Optional<Transaction> transaction = transactionRepository.findByUserId(userId);
        if(transaction.isPresent()){
            return transaction.get();
        }
        else {
            throw new RuntimeException("User (ID:"+userId+") not found");
        }
    }
    public Transaction getTransactionByStudentId(String studentId) {
        Optional<Transaction> transaction = transactionRepository.findByStudentId(studentId);
        if(transaction.isPresent()){
            return transaction.get();
        }
        else {
            throw new RuntimeException("Student (ID:"+studentId+") not found");
        }
    }

    // CRUD
    public Transaction createTransaction(Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Transaction updateTransaction(int id, Transaction updated) {
        return transactionRepository.findById(id)
                .map(tx -> {
                    tx.setType(updated.getType());
                    tx.setStatus(updated.getStatus());
                    tx.setUpdatedAt(LocalDateTime.now());
                    return transactionRepository.save(tx);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

}
