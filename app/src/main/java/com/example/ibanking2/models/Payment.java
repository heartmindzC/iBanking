package com.example.ibanking2.models;

import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("paymentId")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("tuitionId")
    private int tuitionId;
    @SerializedName("amount")
    private double amount;
    @SerializedName("method")
    private String method;
    @SerializedName("status")
    private String status;
    @SerializedName("transactionId")
    private int transactionId;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public Payment(int id, int userId, int tuitionId, double amount, String method, String status, int transactionId, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.tuitionId = tuitionId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTuitionId() {
        return tuitionId;
    }

    public void setTuitionId(int tuitionId) {
        this.tuitionId = tuitionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
