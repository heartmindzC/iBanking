package com.example.ibanking2.models;

public class PaymentRequest {
    private int userId;
    private int tuitionId;
    private String method;
    private double amount;
    private int transactionId;

    public PaymentRequest(int userId, int tuitionId, String method, double amount, int transactionId) {
        this.userId = userId;
        this.tuitionId = tuitionId;
        this.method = method;
        this.amount = amount;
        this.transactionId = transactionId;
    }
}
