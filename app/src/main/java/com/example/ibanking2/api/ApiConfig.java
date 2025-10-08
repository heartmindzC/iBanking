package com.example.ibanking2.api;

import com.example.ibanking2.models.Transaction;

public class ApiConfig {
    private static final String BASE_URL = "http://10.0.2.2:";
    private static final String USER_SERVICE_PORT = "8080";
    private static final String TUITION_SERVICE_PORT = "8081";
    private static final String TRANSACTION_SERVICE_PORT = "8082";
    private static final String PAYMENT_SERVICE_PORT = "8083";

    public static String getUserServiceBaseURL(){
        return BASE_URL + USER_SERVICE_PORT + "/iBanking/";
    }

    public static String getTransactionBaseURL() {
        return BASE_URL + TRANSACTION_SERVICE_PORT + "/iBanking/";
    }

    public static String getTuitionBaseURL() {
        return BASE_URL + TUITION_SERVICE_PORT + "/iBanking/";
    }
}
