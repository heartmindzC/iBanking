package com.example.paymentservice.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_accounts")
public class PaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="account_id")
    private int accountId;
    @Column(name ="user_id",nullable = false, unique = true)
    private int userId; // tham chiếu từ UserService
    @Column(nullable = false, unique = false)
    private Double balance = 0.0;
}
