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
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.LoginManager;
import com.example.ibanking2.models.User;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    TextView tvName, tvStudentId, tvBalance;
    MaterialButton btPayTuition;
    MaterialButton mtTransactionHistory, btSignOut, btStudentInfomation;

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

        // Call api get payments account
        // code here
        //

        tvName = findViewById(R.id.tvName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvBalance = findViewById(R.id.tvBalance);

        tvName.setText(LoginManager.getInstance().getUser().getName());
        tvStudentId.setText(LoginManager.getInstance().getUser().getStudentId());

        // xu li hien so du
        DecimalFormat df = new DecimalFormat("#,###.###");
        tvBalance.setText(df.format(LoginManager.getInstance().balance) + " VND");

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

        btStudentInfomation = findViewById(R.id.btStudentInfomation);
        btStudentInfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserInfomation.class);
                startActivity(intent);
            }
        });

        btSignOut = findViewById(R.id.btSignOut);
        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().clearUser();
                Log.d("Log out", "Log out success");
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // call api get user to
    private User getUser() {
        return null;
    }
}