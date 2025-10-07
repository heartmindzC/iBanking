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

    @SerializedName("paid")
    private boolean paid;

    public Tuition(int id, String userID, double amount, Date date, boolean paid) {
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
