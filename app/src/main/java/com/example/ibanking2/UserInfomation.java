package com.example.ibanking2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ibanking2.models.LoginManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserInfomation extends AppCompatActivity {
    MaterialToolbar mtTransactionHistory;
    EditText etName, etID, etGender, etBirth, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_infomation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
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

        etName = findViewById(R.id.etName);
        etID = findViewById(R.id.etID);
        etGender = findViewById(R.id.etGender);
        etBirth = findViewById(R.id.etBirth);
        etEmail = findViewById(R.id.etEmail);

        etName.setText(LoginManager.getUser().getName());
        etID.setText(LoginManager.getUser().getStudentId());
        if (LoginManager.getUser().getGender().equals("M")) {
            etGender.setText("Nam");
        }
        else {
            etGender.setText("Nữ");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(LoginManager.getUser().getBirthDate());

        etBirth.setText(formattedDate);
        etEmail.setText(LoginManager.getUser().getEmail());

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