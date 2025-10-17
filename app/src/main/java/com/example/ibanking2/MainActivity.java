package com.example.ibanking2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private User user;
    Button btLogin;
    Button btForgetPass;
    EditText etUserName;
    EditText etPassword;
    ProgressBar progressLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btLogin.setText("");
                btLogin.setEnabled(false);

                // hieu ung click nut
                progressLoading = findViewById(R.id.progressLoading);
                progressLoading.setVisibility(View.VISIBLE);

                String studentId = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                HashMap<String, String> loginRequest = new HashMap<String, String>();
                loginRequest.put("studentId", studentId);
                loginRequest.put("password", password);

                ApiService api = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
                Call<User> call = api.login(loginRequest);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            user = response.body();
                            Log.d("Call api login", "Get user success");

                            if (user != null) {
                                LoginManager.getInstance().setUser(user);

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Log.d("Login", "User not exist");
                                showButtonLogin();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Student id or password is wrong", Toast.LENGTH_SHORT).show();
                            Log.d("Login", "Not success");
                            showButtonLogin();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        showButtonLogin();
                    }
                });
            }
        });

        btForgetPass = findViewById(R.id.btRecovery);
        btForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecoveryActivity.class);
                startActivity(intent);
            }
        });
    }

    // roll back hieu ung click nut
    private void showButtonLogin() {
        btLogin.setText("Login");
        btLogin.setEnabled(true);
        progressLoading.setVisibility(View.GONE);
    }

    /*
    public User login(String username, String password) {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = api.login(username, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                }
                else {
                    Toast.makeText(MainActivity.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Can not connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    */
}