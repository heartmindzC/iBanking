package com.example.transactionservice.model;


import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequest {
    private int userId;
    private int tuitionId;
    private String studentId;
    private Date date;
    private double amount;
    private String type;
    private String status;
}
