package com.example.ibanking2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.adapters.TuitionAdapter;
import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends AppCompatActivity {
    MaterialToolbar mtb;
    Button btGetTuition;
    EditText etFindByStudentId;
    RecyclerView rvTuitions;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        btLogin = findViewById(R.id.btLogin);
//        btLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btForgetPass = findViewById(R.id.btRecovery);
//        btForgetPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RecoveryActivity.class);
//                startActivity(intent);
//            }
//        });


        btGetTuition = findViewById(R.id.btGetTuition);
        btGetTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFindByStudentId = findViewById(R.id.etFindByStudentId);
                String studentId = etFindByStudentId.getText().toString();

                // call api user to find user by student id
                ApiService apiUser = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
                Call<User> call = apiUser.getUserByStudentId(studentId);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            user = response.body();
                        }
                        else {
                            Log.d("Call API User: ", "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("Call api user", "Error");
                        t.printStackTrace();
                    }
                });

                if (user != null) {
                    // Call api tuition
                    ApiService apiTuition = ApiClient.getClient(ApiConfig.getTuitionBaseURL()).create(ApiService.class);
                    Call<List<Tuition>> callTuition = apiTuition.getTuitionByUserId(user.getId());
                    callTuition.enqueue(new Callback<List<Tuition>>() {
                        @Override
                        public void onResponse(Call<List<Tuition>> call, Response<List<Tuition>> response) {
                            if (response.isSuccessful()) {
                                List<Tuition> tuitions = response.body();
                                TuitionAdapter adapter = new TuitionAdapter(tuitions, user);

                                rvTuitions = findViewById(R.id.rvTuitions);
                                rvTuitions.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
                                rvTuitions.addItemDecoration(new DividerItemDecoration(TransactionActivity.this, DividerItemDecoration.VERTICAL));
                                rvTuitions.setAdapter(adapter);
                            }
                            else {
                                Log.d("Call API tuitions: ", "Error");
                                Log.d("Call API tuitions: ", "Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Tuition>> call, Throwable t) {
                            Log.d("Call api tuition: ", "Error");
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        mtb = findViewById(R.id.mtbTuitionPayment);
        setSupportActionBar(mtb);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}