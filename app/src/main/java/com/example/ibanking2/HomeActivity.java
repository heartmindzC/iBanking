package com.example.ibanking2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.User;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    TextView tvName, tvStudentId;
    MaterialButton btPayTuition;
    MaterialButton mtTransactionHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.tvName);
        tvStudentId = findViewById(R.id.tvStudentId);

        ApiService api = ApiClient.getClient().create(ApiService.class);
        Call<User> call = api.getUserByStudentId("SV001");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("API", "call API success");
                    User user = response.body();
                    tvName.setText(user.getName());
                    tvStudentId.setText("" + user.getStudentId());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });


        btPayTuition = findViewById(R.id.btPayTuition);
        btPayTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });

        mtTransactionHistory = findViewById(R.id.btHistory);
        mtTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TransactionHistory.class);
                startActivity(intent);
            }
        });
    }
}