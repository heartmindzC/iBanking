package com.example.ibanking2.api;

import com.example.ibanking2.models.Payment;
import com.example.ibanking2.models.PaymentRequest;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.TransactionRequest;
import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //Call API for user
    @GET("users/userByStudentId/{studentId}")
    Call<User> getUserByStudentId(@Path("studentId") String studentId);

    @POST("users/login?")
    Call<User> login (@Body HashMap<String, String> request);



    // Call api for tuition
    @GET("tuitions/getTuitionByUserId/{userId}")
    Call<List<Tuition>> getTuitionByUserId(@Path("userId") int userId);

    @PUT("tuitions/updateTuitionIsPaidById/{id}/{paid}")
    Call<ResponseBody> updateTuitionIsPaid(@Path("id") int id, @Path("paid") boolean paid);


    // Call api for transaction
    @GET("transactions/getTransactionByUserId/{userId}")
    Call<List<Transaction>> getTransactionByUserId(@Path("userId") int userId);

    @POST("transactions/create?")
    Call<Transaction> createTransaction(@Body TransactionRequest transaction);

    @PUT("transactions/update/{id}")
    Call<Transaction> updateTransaction(@Path("id") int transactionId, @Body Transaction updTransaction);



    // Call Api pyament-service
    @GET("payments/getBalanceByUserId/{userId}")
    Call<Double> getBalanceByUserId(@Path("userId") int id);

    @POST("payments/createPayment?")
    Call<Payment> createPayment(@Body PaymentRequest paymentRequest);

    @PUT("payments/updateBalance/{userId}/{updBalance}")
    Call<ResponseBody> updateBalance(@Path("userId") int userId, @Path("updBalance") double udpBalance);

    @PUT("payments/update/{id}/status")
    Call<Payment> updatePayment (@Path("id")int paymentId, @Query("status") String status);



    // Call api xu li otp
    @POST("otp/generate?")
    Call<Map<String, String>> genarateOTP(@Body Map<String, Object> request);

    @POST("otp/verify?")
    Call<Map<String, String>> verifyOTP(@Body Map<String, Object> request);



    // Call api xu li gui email
    @POST("notifications/send-transaction?")
    Call<ResponseBody> sendOTPAfterTransaction(@Body Map<String,String> request);
}
