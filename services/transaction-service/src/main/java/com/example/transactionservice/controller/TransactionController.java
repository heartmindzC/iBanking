package com.example.transactionservice.controller;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionRequest;
import com.example.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired

    private TransactionService transactionService;
    @GetMapping("/getTransactionById/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @GetMapping("/getTransactionByTuitionId/{tuitionId}")
    public ResponseEntity<Transaction> getTransactionByTuitionId(@PathVariable int tuitionId){
        return ResponseEntity.ok(transactionService.getTransactionByTuitionId(tuitionId));
    }
    @GetMapping("/getTransactionByStudentId/{studentId}")
    public ResponseEntity<Transaction> getTransactionByStudentId(@PathVariable String studentId){
        return ResponseEntity.ok(transactionService.getTransactionByStudentId(studentId));
    }
    @GetMapping("/getTransactionByUserId/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionByUserId(@PathVariable int userId){
        return ResponseEntity.ok(transactionService.getTransactionByUserId(userId));
    }

    // === CRUD ===

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest request) {
        Transaction created = transactionService.createTransaction(
                request.getUserId(),
                request.getTuitionId(),
                request.getStudentId(),
                request.getDate(),
                request.getAmount(),
                request.getType(),
                request.getStatus()
        );
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int id, @RequestBody Transaction updatedTransaction) {
        Transaction updated = transactionService.updateTransaction(id, updatedTransaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
