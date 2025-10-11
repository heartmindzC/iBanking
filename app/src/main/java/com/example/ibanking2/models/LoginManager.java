package com.example.ibanking2.models;

import java.util.ArrayList;

public class LoginManager {
    private static User user;
    private static PaymentAccount paymentAccount;
    private static LoginManager instance;

    private LoginManager() {
        this.user = user;
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginManager.user = user;
    }

    public void clearUser() {
        this.user = null;
    }
}
