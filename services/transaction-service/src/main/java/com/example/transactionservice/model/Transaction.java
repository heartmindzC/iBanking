package com.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,  unique = false)
    // id nguoi thanh toan
    private int userId;
    @Column(nullable = false,  unique = false)
    private int tuitionId;
    // ma sv nguoi duoc thanh toan
    @Column(nullable = false,  unique = false)
    private String studentId;
    @Column(nullable = false,  unique = false)
    private Date date;
    @Column(nullable = false,  unique = false)
    private double amount;
    @Column(nullable = false,  unique = false)
    private String type; // e.g. "PAYMENT", "REFUND"
    @Column(nullable = false,  unique = false)
    private String status; // "PENDING", "SUCCESS", "FAILED"
    @Column(nullable = false,  unique = false)
    private LocalDateTime createdAt;
    @Column(nullable = false,  unique = false)
    private LocalDateTime updatedAt;

}
