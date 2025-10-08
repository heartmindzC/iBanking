package com.example.ibanking2.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class Transaction {
    @SerializedName("id")
    private int transactionId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("tuitionId")
    private int tuitionId;

    @SerializedName("studentId")
    private String studentId;

    @SerializedName("date")
    private Date date;

    @SerializedName("amount")
    private double amount;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public Transaction(int transactionId, int userId, int tuitionId, String studentId, Date date, double amount, String type, String status, String createdAt, String updatedAt) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.tuitionId = tuitionId;
        this.studentId = studentId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
}
