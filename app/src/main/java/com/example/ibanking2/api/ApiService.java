package com.example.ibanking2.api;

import com.example.ibanking2.models.LoginRequest;
import com.example.ibanking2.models.Payment;
import com.example.ibanking2.models.PaymentRequest;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.TransactionRequest;
import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //Call API for user
    @GET("users/userByStudentId/{studentId}")
    Call<User> getUserByStudentId(@Path("studentId") String studentId);

    @GET("users/userById/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("users/login?")
    Call<User> login (@Body HashMap<String, String> request);



    // Call api for tuition
    @GET("tuitions/getTuitionByUserId/{userId}")
    Call<List<Tuition>> getTuitionByUserId(@Path("userId") int userId);

    @GET("tuitions/getTuitionById/{id}")
    Call<Tuition> getTuitionById(@Path("id") int id);



    // Call api for transaction
    @GET("transactions/getTransactionByUserId/{userId}")
    Call<List<Transaction>> getTransactionByUserId(@Path("userId") int userId);

    @GET("transactions/getTransactionById/{id}")
    Call<Transaction> getTransactionById(@Path("id") int id);

    @POST("transactions/create?")
    Call<Transaction> createTransaction(@Body TransactionRequest transaction);



    // Call Api pyament-service
    @GET("payments/getBalanceByUserId/{userId}")
    Call<Double> getBalanceByUserId(@Path("userId") int id);

    @POST("payments/createPayment?")
    Call<Payment> createPayment(@Body PaymentRequest paymentRequest);



    // Call api xu li otp
    @POST("otp/generate?")
    Call<Map<String, String>> genarateOTP(@Body Map<String, Object> request);

    @POST("otp/verify?")
    Call<Map<String, String>> verifyOTP(@Body Map<String, Object> request);
}
