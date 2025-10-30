package com.example.ibanking2.models;

import android.util.Log;

import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginManager {
    private static User user;
    public static double balance = 0.0;
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
        setBalance();
    }

    public void clearUser() {
        this.user = null;
        balance = 0.0;
    }

    public static void setBalance() {
        long startTime = System.currentTimeMillis();
        long duration = 500;

        while (System.currentTimeMillis() - startTime < duration) {
            ApiService apiGetBalance = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
            Call<Double> callApiGetBalance = apiGetBalance.getBalanceByUserId(user.getId());
            callApiGetBalance.enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("Call API get Balance: ", "Success");
                        balance = response.body();
                    }
                    else {
                        Log.d("Call API get Balance: ", "Error");
                    }
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
