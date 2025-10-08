package com.example.paymentservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name ="payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private int id;

    @Column(name = "user_id",nullable = false, unique = false)
    private int userId;

    @Column(name = "tuition_id",nullable = false, unique = false)
    private int tuitionId;

    @Column(nullable = false, unique = false)
    private double amount;

    @Column(nullable = false, unique = false)
    private String method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false, unique = false)
    private int transactionId;

    @Column(name= "created_at",nullable = false, unique = false)
    private LocalDateTime createdAt;

    @Column(name= "updated_at",nullable = false, unique = false)
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
