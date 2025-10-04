package com.example.ibanking2.api;

import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/userByStudentId/{studentId}")
    Call<User> getUserByStudentId(@Path("studentId") String studentId);

    @GET("users/userById/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("tuitions/getTuitionByUserId/{userId}")
    Call<Tuition> getTuitionByUserId(@Path("userId") int userId);

    @GET("tuitions/getTuitionById/{id}")
    Call<Tuition> getTuitionById(@Path("id") int id);
}
