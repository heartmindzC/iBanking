package com.example.ibanking2.models;

import java.util.Date;

public class TransactionRequest {
    // id cua nguoi dong
    private int userId;
    private int tuitionId;
    // ma sv nguoi duoc dong
    private String studentId;
    private Date date;
    private double amount;
    private String type;
    private String status;

    public TransactionRequest(int userId, int tuitionId, String studentId, Date date, double amount, String type, String status) {
        this.userId = userId;
        this.tuitionId = tuitionId;
        this.studentId = studentId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }
}
