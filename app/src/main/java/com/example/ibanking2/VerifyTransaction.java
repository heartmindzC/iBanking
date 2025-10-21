package com.example.ibanking2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.ibanking2.models.Payment;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.TransactionProcessing;
import com.example.ibanking2.models.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//
//public class VerifyTransaction extends AppCompatActivity {
//
//    TextView tvNameUserPay, tvNameUserPaid, tvAmount, tvContent, tvDate;
//    Button btCancel, btSubmit;
//    EditText etNum1, etNum2, etNum3, etNum4, etNum5, etNum6;
//
//    Payment payment;
//    Transaction transaction;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_verify_transaction);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        Intent intent = getIntent();
//        int transactionId = intent.getIntExtra("transactionId", -1);
//        int paymentId = intent.getIntExtra("paymentId", -1);
//
//        if (transactionId == -1) {
//            // giao dich loi
//            // code here
//        }
//
//        if (paymentId == -1) {
//            // giao dich loi
//            // code here
//        }
//
//        getTransaction(transactionId);
//        getPayment(paymentId);
//
//        etNum1 = findViewById(R.id.etNum1);
//        etNum2 = findViewById(R.id.etNum2);
//        etNum3 = findViewById(R.id.etNum3);
//        etNum4 = findViewById(R.id.etNum4);
//        etNum5 = findViewById(R.id.etNum5);
//        etNum6 = findViewById(R.id.etNum6);
//
//        tvNameUserPay = findViewById(R.id.tvNameUserPay);
//        tvNameUserPaid = findViewById(R.id.tvNameUserPaid);
//        tvAmount = findViewById(R.id.tvAmount);
//        tvContent = findViewById(R.id.tvContent);
//        tvDate = findViewById(R.id.tvDate);
//
//        btCancel = findViewById(R.id.btCancel);
//        btSubmit = findViewById(R.id.btSubmit);
//
//        final EditText[] otpFields = new EditText[]{etNum1, etNum2, etNum3, etNum4, etNum5, etNum6};
//
//        for (int i = 0; i < otpFields.length; i++) {
//            final int currentIndex = i;
//            otpFields[currentIndex].addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    // Không cần xử lý
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    // Không cần xử lý
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    // Nếu người dùng đã nhập 1 ký tự và đây không phải ô cuối cùng
//                    if (s.length() == 1 && currentIndex < otpFields.length - 1) {
//                        // Chuyển focus đến ô tiếp theo
//                        otpFields[currentIndex + 1].requestFocus();
//                    }
//                }
//            });
//        }
//
//        etNum1.requestFocus();
//        tvNameUserPay.setText(LoginManager.getUser().getStudentId() + "\n" + LoginManager.getUser().getName());
//
//        ApiService apiGetUserPaid = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
//        Call<User> callApiGetUserPaid = apiGetUserPaid.getUserByStudentId(transaction.getStudentId());
//        callApiGetUserPaid.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("Call api get user inform", "Success");
//                    User userPaid = response.body();
//                    tvNameUserPaid.setText(userPaid.getStudentId() + "\n" + userPaid.getName());
//                    tvContent.setText("THANH TOAN HOC PHI CHO SINH VIEN " + userPaid.getStudentId());
//
//                }
//                else {
////                    Log.d("Call api get user inform", "Error");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//        DecimalFormat df = new DecimalFormat("#,###.###");
//        tvAmount.setText("" + df.format(transaction.getAmount()) + "VND");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = sdf.format(transaction.getDate());
//        tvDate.setText(formattedDate);
//
//        btCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ApiService apiService = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
//                callApiUpdateUserActive(apiService, false, LoginManager.getUser().getId());
//                TransactionProcessing.updateTransactionStatus(transaction, "FAILED");
//                TransactionProcessing.updatePaymentStatus(payment, "CANCEL");
//            }
//        });
//
//        btSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//    }
//
//    private void callApiUpdateUserActive(ApiService apiService, boolean status, int userId){
//        Call<ResponseBody> callApiUpdateactiveUser = apiService.updateActiveByUserId(userId, status);
//        callApiUpdateactiveUser.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("Call api update user active: ", "Success");
//                }
//                else {
//                    Log.d("Call api update user active: ", "Error");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    private void getTransaction(int transactionId) {
//        // call api get Transaction by id from previous intent
//        ApiService apiFindTransaction = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
//        Call<Transaction> callApiFindTransaction = apiFindTransaction.getTransactionById(transactionId);
//        callApiFindTransaction.enqueue(new Callback<Transaction>() {
//            @Override
//            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("Call api get transaction: ", "Success");
//                    transaction = response.body();
//                }
//                else {
//                    Log.d("Call api get transaction: ", "Error");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Transaction> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    private void getPayment(int paymentId) {
//        ApiService apiGetPayment = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
//        Call<Payment> callApiGetPayment = apiGetPayment.getPaymentById(paymentId);
//        callApiGetPayment.enqueue(new Callback<Payment>() {
//            @Override
//            public void onResponse(Call<Payment> call, Response<Payment> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("Call api get transaction: ", "Success");
//                    payment = response.body();
//                }
//                else {
//                    Log.d("Call api get transaction: ", "Error");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Payment> call, Throwable t) {
//
//            }
//        });
//    }
//}

//package com.example.ibanking2;

// ... (các import của bạn giữ nguyên)
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger; // <-- THÊM IMPORT NÀY

public class VerifyTransaction extends AppCompatActivity {

    TextView tvNameUserPay, tvNameUserPaid, tvAmount, tvContent, tvDate, tvCountDown;
    Button btCancel, btSubmit;
    EditText etNum1, etNum2, etNum3, etNum4, etNum5, etNum6;

    // Biến để giữ kết quả
    private Payment payment;
    private Transaction transaction;
    private CountDownTimer countDownTimer;

    // Bộ đếm để theo dõi 2 lệnh gọi API
    private AtomicInteger apiCallCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_transaction);

        Intent intent = getIntent();
        int transactionId = intent.getIntExtra("transactionId", -1);
        int paymentId = intent.getIntExtra("paymentId", -1);

        if (transactionId == -1 || paymentId == -1) {
            Toast.makeText(this, "Lỗi: ID giao dịch không hợp lệ.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setupIndependentUI();

        apiCallCounter = new AtomicInteger(2);
        loadData(transactionId, paymentId);
    }


    private void setupIndependentUI() {
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        etNum3 = findViewById(R.id.etNum3);
        etNum4 = findViewById(R.id.etNum4);
        etNum5 = findViewById(R.id.etNum5);
        etNum6 = findViewById(R.id.etNum6);

        tvNameUserPay = findViewById(R.id.tvNameUserPay);
        tvNameUserPaid = findViewById(R.id.tvNameUserPaid);
        tvAmount = findViewById(R.id.tvAmount);
        tvContent = findViewById(R.id.tvContent);
        tvDate = findViewById(R.id.tvDate);

        btCancel = findViewById(R.id.btCancel);
        btSubmit = findViewById(R.id.btSubmit);

        final EditText[] otpFields = new EditText[]{etNum1, etNum2, etNum3, etNum4, etNum5, etNum6};
        for (int i = 0; i < otpFields.length; i++) {
            final int currentIndex = i;
            otpFields[currentIndex].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && currentIndex < otpFields.length - 1) {
                        otpFields[currentIndex + 1].requestFocus();
                    }
                }
            });
        }
        etNum1.requestFocus();

        tvNameUserPay.setText(LoginManager.getUser().getStudentId() + "\n" + LoginManager.getUser().getName());

        tvCountDown = findViewById(R.id.tvCountDown);
        long millisInFuture = TimeUnit.MINUTES.toMillis(5);
        long countDownInterval = 1000;

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes);

                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                tvCountDown.setText(formattedTime);
            }

            @Override
            public void onFinish() {
                tvCountDown.setText("00:00");
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void loadData(int transactionId, int paymentId) {
        ApiService apiFindTransaction = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
        Call<Transaction> callApiFindTransaction = apiFindTransaction.getTransactionById(transactionId);
        callApiFindTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transaction = response.body();
                }
                checkIfAllApisFinished();
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.e("API_ERROR", "getTransaction failed", t);
                checkIfAllApisFinished();
            }
        });

        ApiService apiGetPayment = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
        Call<Payment> callApiGetPayment = apiGetPayment.getPaymentById(paymentId);
        callApiGetPayment.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    payment = response.body();
                }
                checkIfAllApisFinished();
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Log.e("API_ERROR", "getPayment failed", t);
                checkIfAllApisFinished();
            }
        });
    }

    // dam bao du lieu duoc load sau khi hoan thanh goi api
    private void checkIfAllApisFinished() {
        if (apiCallCounter.decrementAndGet() == 0) {
            runOnUiThread(() -> {
                if (transaction != null && payment != null) {
                    setupDependentUI();
                } else {
                    Toast.makeText(VerifyTransaction.this, "Lỗi: Không thể tải thông tin giao dịch.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    private void setupDependentUI() {
        ApiService apiGetUserPaid = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
        Call<User> callApiGetUserPaid = apiGetUserPaid.getUserByStudentId(transaction.getStudentId());
        callApiGetUserPaid.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api get user inform", "Success");
                    User userPaid = response.body();
                    tvNameUserPaid.setText(userPaid.getStudentId() + "\n" + userPaid.getName());
                    tvContent.setText("THANH TOAN HOC PHI CHO SINH VIEN " + userPaid.getStudentId());
                } else {
                    Log.d("Call api get user inform", "Error");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });

        DecimalFormat df = new DecimalFormat("#,###.###");
        tvAmount.setText("" + df.format(transaction.getAmount()) + "VND");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(transaction.getDate());
        tvDate.setText(formattedDate);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService apiService = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
                callApiUpdateUserActive(apiService, false, LoginManager.getUser().getId());
                TransactionProcessing.updateTransactionStatus(transaction, "FAILED");
                TransactionProcessing.updatePaymentStatus(payment, "CANCEL");
                TransactionProcessing.updateStatusTuition(payment.getTuitionId(), "NOT_PAY");
                Intent intent = new Intent(VerifyTransaction.this, TransactionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = etNum1.getText().toString() +
                        etNum2.getText().toString() +
                        etNum3.getText().toString() +
                        etNum4.getText().toString() +
                        etNum5.getText().toString() +
                        etNum6.getText().toString();

                if (otp.length() == 6) {
                    Map<String, Object> otpRequest = new HashMap<String, Object>();
                    otpRequest.put("userId", LoginManager.getUser().getId());
                    otpRequest.put("purpose", "Verify transaction");
                    otpRequest.put("code", otp);

                    ApiService apiVerifyOTP = ApiClient.getClient(ApiConfig.getOTPBaseURL()).create(ApiService.class);
                    Call<Map<String, String>> callApiVerifyOTP = apiVerifyOTP.verifyOTP(otpRequest);
                    callApiVerifyOTP.enqueue(new Callback<Map<String, String>>() {
                        @Override
                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Map<String, String> result = response.body();
                                Boolean flag = Boolean.parseBoolean(result.get("verified"));

                                if (flag) {
                                    Toast.makeText(view.getContext(), "Nhap ma OTP dung", Toast.LENGTH_SHORT).show();

                                    // xu li sau khi nhap otp dung
                                    updateBalance(payment);
                                    TransactionProcessing.updatePaymentStatus(payment, "SUCCESS");
                                    TransactionProcessing.updateTransactionStatus(transaction, "SUCCESS");
                                    TransactionProcessing.updateStatusTuition(payment.getTuitionId(), "PAID");
                                    sendEmailVerifySuccess(transaction);

                                    Intent intent = new Intent(view.getContext(), PaymentSuccess.class);
                                    view.getContext().startActivity(intent);
                                    finish();
                                }
                                else {
                                    TransactionProcessing.updateStatusTuition(payment.getTuitionId(), "NOT_PAY");
                                    TransactionProcessing.updateTransactionStatus(transaction, "FAILED");
                                    TransactionProcessing.updatePaymentStatus(payment, "CANCEL");
                                    Toast.makeText(view.getContext(), "Nhap ma OTP sai", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                TransactionProcessing.updateStatusTuition(payment.getTuitionId(), "NOT_PAY");
                                TransactionProcessing.updateTransactionStatus(transaction, "FAILED");
                                TransactionProcessing.updatePaymentStatus(payment, "FAILED");
                                Log.d("Call api verify OTP", "Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                            // khong xac thu otp duoc
                            TransactionProcessing.updateTransactionStatus(transaction, "FAILED");
                            TransactionProcessing.updatePaymentStatus(payment, "FAILED");
                            TransactionProcessing.updateStatusTuition(payment.getTuitionId(), "NOT_PAY");
                            t.printStackTrace();
                        }
                    });

                } else {
                    Toast.makeText(view.getContext(), "Mã OTP phải đủ 6 chữ số", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callApiUpdateUserActive(ApiService apiService, boolean status, int userId){
        Call<ResponseBody> callApiUpdateactiveUser = apiService.updateActiveByUserId(userId, status);
        callApiUpdateactiveUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api update user active: ", "Success");
                }
                else {
                    Log.d("Call api update user active: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    
    private void updateBalance(Payment payment) {
        // Call lai api check so du va tru tien
        ApiService apiUpdateBalance = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
        Call<Double> callApiGetBalance = apiUpdateBalance.getBalanceByUserId(LoginManager.getUser().getId());
        callApiGetBalance.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api get balance: ", "Success");
                    Double balance = response.body();
                    if (balance > payment.getAmount()) {
                        Double newBalance = balance - payment.getAmount();

                        // Call api update new balance
                        Call<ResponseBody> callApiUpdateBalance = apiUpdateBalance.updateBalance(LoginManager.getUser().getId(), newBalance);
                        callApiUpdateBalance.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Log.d("Call Api update balance: ", "Success");

                                    ApiService apiUpdateUserActive = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
                                    callApiUpdateUserActive(apiUpdateUserActive, false, LoginManager.getUser().getId());
                                }
                                else {
                                    Log.d("Call Api update balance: ", "Error");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
                else {
                    Log.d("Call api get balance: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void sendEmailVerifySuccess(Transaction transaction) {
        Map<String, String> emailRequest = new HashMap<String, String>();
        emailRequest.put("toEmail", LoginManager.getUser().getEmail());
        emailRequest.put("transactionId", transaction.getTransactionId() + "");
        ApiService apiSendEmailAfterTransaction = ApiClient.getClient(ApiConfig.getEmailServicePort()).create(ApiService.class);
        Call<ResponseBody> callApiSendEmail = apiSendEmailAfterTransaction.sendOTPAfterTransaction(emailRequest);
        callApiSendEmail.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api send email after transaction: ", "Success");
                }
                else {
                    Log.d("Call api send email after transaction: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}