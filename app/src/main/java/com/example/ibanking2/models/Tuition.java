package com.example.ibanking2.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Tuition {
    @SerializedName("id")
    private int id;

    @SerializedName("userId")
    private String userID;

    @SerializedName("amount")
    private double amount;

    @SerializedName("date")
    private Date date;

    // DA_THANH_TOAN, CHO_THANH_TOAN, CHUA_THANH_TOAN
    @SerializedName("status")
    private String paid;

    public Tuition(int id, String userID, double amount, Date date, String paid) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.date = date;
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public String isPaid() {
        return paid;
    }

    public Date getDate() {
        return date;
    }
}
