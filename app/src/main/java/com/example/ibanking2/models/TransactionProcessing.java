package com.example.ibanking2.models;

import android.util.Log;

import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionProcessing {
    public static void updateTransactionStatus(Transaction transaction, String status){
        transaction.setStatus(status);

        ApiService apiUpdateTransaction = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
        Call<Transaction> callApiUpdateTransaction = apiUpdateTransaction.updateTransaction(transaction.getTransactionId(), transaction);
        callApiUpdateTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api update transaction: ", "Success");
                    Log.d("Call api update transaction: ", response.body().toString());
                }
                else {
                    Log.d("Call api update transaction: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public  static void updatePaymentStatus(Payment payment, String status){
        payment.setStatus(status);

        ApiService apiUpdatePayment = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
        Log.d("Payment: ", payment.getId() + "");
        Call<Payment> callApiUpdatePayment = apiUpdatePayment.updatePayment(payment.getId(), payment.getStatus());
        callApiUpdatePayment.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api update Payment: ", "Success");
//                    Log.d("Call api update Payment: ", response.body().toString());
                }
                else {
                    Log.d("Call api update Payment: ", "Error code: " + response.code() +
                            " | body: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void updateStatusTuition(int tuitionId, String newStatus){
        ApiService apiUpdateTuitionIsPaid = ApiClient.getClient(ApiConfig.getTuitionBaseURL()).create(ApiService.class);
        Call<ResponseBody> callApiUpdateTuitionIsPaid = apiUpdateTuitionIsPaid.updateTuitionIsPaid(tuitionId, newStatus);
        callApiUpdateTuitionIsPaid.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call Api update tuition is_paid: ", "Success");
                }
                else {
                    Log.d("Call Api update tuition is_paid: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
