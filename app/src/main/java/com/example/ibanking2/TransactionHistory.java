package com.example.ibanking2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.adapters.TransactionAdapter;
import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.LoginManager;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.User;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistory extends AppCompatActivity {

    MaterialToolbar mtTransactionHistory;
    RecyclerView rvTransactions;
    TextView tvStudentId, tvName, tvBalance;

    private static final User userLogin = LoginManager.getInstance().getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mtTransactionHistory = findViewById(R.id.mtTransactionHistory);
        setSupportActionBar(mtTransactionHistory);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tvStudentId = findViewById(R.id.tvStudentId);
        tvStudentId.setText(userLogin.getStudentId());
        tvName = findViewById(R.id.tvName);
        tvName.setText(userLogin.getName());
        tvBalance = findViewById(R.id.tvBalance);
        tvBalance.setText(LoginManager.getInstance().balance + " VND");

        rvTransactions = findViewById(R.id.rvTransactions);

        ApiService api = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
        Call<List<Transaction>> call = api.getTransactionByUserId(1);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()){
                    List<Transaction> transactions = response.body();
                    TransactionAdapter adapter = new TransactionAdapter(transactions);

                    rvTransactions.setLayoutManager(new LinearLayoutManager(TransactionHistory.this));
                    rvTransactions.addItemDecoration(new DividerItemDecoration(TransactionHistory.this, DividerItemDecoration.VERTICAL));
                    rvTransactions.setAdapter(adapter);
                }
                else {
                    Log.d("Call Transaction API: ", "ERROR");
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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


//        Call<Transaction> call = api.getTransactionById(1);
//        Log.d("API_CALL", ApiConfig.getTransactionBaseURL());
//        Log.d("API_CALL", call.request().url().toString());
//        call.enqueue(new Callback<Transaction>() {
//            @Override
//            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
//                if (response.isSuccessful()) {
//                    Transaction transaction = response.body();
//                    List<Transaction> transactions = new ArrayList<>();
//                    transactions.add(transaction);
//
//                    TransactionAdapter adapter = new TransactionAdapter(transactions);
//                    rvTransactions.setLayoutManager(new LinearLayoutManager(TransactionHistory.this));
//                    rvTransactions.addItemDecoration(new DividerItemDecoration(TransactionHistory.this, DividerItemDecoration.VERTICAL));
//                    rvTransactions.setAdapter(adapter);
//                }
//                else {
//                   Log.d("Call API Transaction: ", "ERROR");
//                    try {
//                        Log.e("API_RESPONSE", "Error Body: " + response.errorBody().string());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Transaction> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });