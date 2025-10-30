package com.example.ibanking2.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.PaymentSuccess;
import com.example.ibanking2.R;
import com.example.ibanking2.VerifyTransaction;
import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.LoginManager;
import com.example.ibanking2.models.Payment;
import com.example.ibanking2.models.PaymentRequest;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.TransactionProcessing;
import com.example.ibanking2.models.TransactionRequest;
import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuitionAdapter extends RecyclerView.Adapter<TuitionHolder> {
    private List<Tuition> tuitions;
    private User user;
    private final Activity activity;

    // Khi click nut tim se truyen se call api tim user sau do truyen vao adapter
    public TuitionAdapter(List<Tuition> tuitions, User user, Activity activity) {
        this.tuitions = tuitions;
        this.user = user;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TuitionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuition_layout, parent, false);
        return new TuitionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuitionHolder holder, int position) {
        Tuition tuition = tuitions.get(position);

        holder.tvStudentId.setText(user.getStudentId());
        holder.tvName.setText(user.getName());
        holder.tvGender.setText(user.getGender());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(tuition.getDate());
        holder.tvDateOfBirth.setText(formattedDate);

        holder.tvClass.setText(user.getClasses());
        DecimalFormat df = new DecimalFormat("#,###.###");
        holder.tvAmount.setText("" + df.format(tuition.getAmount()) + " VND");
        Log.d("Tuition", tuition.isPaid());
        if (tuition.isPaid().equals("PAID")) {
            holder.tvStatus.setText("ĐÃ THANH TOAN");
        } else if (tuition.isPaid().equals("NOT_PAY")) {
            holder.tvStatus.setText("CHƯA THANH TOAN");
            holder.tvStatus.setTextColor(Color.RED);
        }
        else {
            holder.tvStatus.setText("ĐANG THANH TOAN");
        }

        // gach hoc phi va an nut thanh toan
        if (tuition.isPaid().equals("PAYING") || tuition.isPaid().equals("PAID")) {
            holder.btPayment.setVisibility(View.GONE);
            holder.tvAmount.setPaintFlags(holder.tvAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        //
        holder.btPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), VerifyTransaction.class);
//                activity.startActivity(intent);

                // call api thanh toan hoc phi
                holder.btPayment.setEnabled(false);
                holder.btPayment.setText("Đang xử lí...");
                holder.progressLoading.setVisibility(View.VISIBLE);   // hieu ung click nut

                ApiService apiGetTuition = ApiClient.getClient(ApiConfig.getTuitionBaseURL()).create(ApiService.class);
                Call<Tuition> callApiGetTuition = apiGetTuition.getTuitionById(tuition.getId());
                callApiGetTuition.enqueue(new Callback<Tuition>() {
                    @Override
                    public void onResponse(Call<Tuition> call, Response<Tuition> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("Call api get tuition: ", "Success");
                            Tuition getTuition = response.body();
                            if (getTuition.isPaid().equals("PAID")) {
                                Toast.makeText(view.getContext(), "Hoc phi da duoc thanh toan", Toast.LENGTH_SHORT).show();
                                showButtonPayment(holder);
                            }
                            else if (getTuition.isPaid().equals("PAYING")){
                                Toast.makeText(view.getContext(), "Hoc phi dan duoc thanh toan o tai khoan khac", Toast.LENGTH_SHORT).show();
                                showButtonPayment(holder);
                            }
                            else {
                                Call<ResponseBody> callUpdateTuitionStatus = apiGetTuition.updateTuitionIsPaid(getTuition.getId(), "PAYING");
                                callUpdateTuitionStatus.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            Log.d("Call api update tuitions: ", "Success");

                                            // call api kiem tra flag_user_in_transaction 1ktra 2update
                                            ApiService apiGetUserActive = ApiClient.getClient(ApiConfig.getUserServiceBaseURL()).create(ApiService.class);
                                            Call<Boolean> callApiGetUserActive = apiGetUserActive.getActiveByUserId(LoginManager.getUser().getId());
                                            callApiGetUserActive.enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if (response.isSuccessful() && response.body() != null) {
                                                        boolean active = response.body();
                                                        if (active) {
                                                            Toast.makeText(view.getContext(), "Tai khoan dang thuc hien giao dich", Toast.LENGTH_SHORT).show();
                                                            showButtonPayment(holder);
                                                            return;
                                                        }

                                                        callApiUpdateUserActive(apiGetUserActive, true, LoginManager.getUser().getId());

                                                        // Tao mot new transaction
                                                        TransactionRequest transactionRequest = createTransactionRequest(tuition.getId(), user.getStudentId(), tuition.getAmount());

                                                        // call api tao giao dich
                                                        ApiService apiCreateTransaction = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
                                                        Call<Transaction> callApiCreateTransaction = apiCreateTransaction.createTransaction(transactionRequest);
                                                        callApiCreateTransaction.enqueue(new Callback<Transaction>() {
                                                            @Override
                                                            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                                                                if (response.isSuccessful() && response.body() != null) {
                                                                    Log.d("Call api create transaction: ", "Success");
                                                                    Transaction newTransaction = response.body();

                                                                    // Call api va chack balance
                                                                    ApiService apiGetBalance = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
                                                                    Call<Double> callApiGetBalance = apiGetBalance.getBalanceByUserId(LoginManager.getUser().getId());
                                                                    callApiGetBalance.enqueue(new Callback<Double>() {
                                                                        @Override
                                                                        public void onResponse(Call<Double> call, Response<Double> response) {
                                                                            if (response.isSuccessful() && response.body() != null) {
                                                                                Log.d("Call api get balance: ", "Success");
                                                                                Double balance = response.body();

                                                                                // Neu balance k du thi huy transaction va dung giao dich
                                                                                if (balance < tuition.getAmount()) {
                                                                                    TransactionProcessing.updateTransactionStatus(newTransaction,"FAILED");
                                                                                    Log.d("Notification: ", "Not enough balance");
                                                                                    Toast.makeText(view.getContext(), "So du khong du", Toast.LENGTH_SHORT).show();
                                                                                    callApiUpdateUserActive(apiGetUserActive, false, LoginManager.getUser().getId());
                                                                                    TransactionProcessing.updateStatusTuition(tuition.getId(), "NOT_PAY");
                                                                                    showButtonPayment(holder);
                                                                                    return;
                                                                                }

                                                                                PaymentRequest paymentRequest = createPaymentRequest(tuition.getId(), tuition.getAmount(), newTransaction.getTransactionId());

                                                                                ApiService apiCreatePayment = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
                                                                                Call<Payment> callApiCreatePayment = apiCreatePayment.createPayment(paymentRequest);
                                                                                callApiCreatePayment.enqueue(new Callback<Payment>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<Payment> call, Response<Payment> response) {
                                                                                        if (response.isSuccessful() && response.body() != null) {
                                                                                            Log.d("Call api create payment", "Success");
                                                                                            Payment newPayment = response.body();
                                                                                            Log.d("New payment: ", newPayment.getId() + "");

                                                                                            Map<String, Object> otpRequest = new HashMap<String, Object>();
                                                                                            otpRequest.put("email", LoginManager.getUser().getEmail());
                                                                                            otpRequest.put("purpose", "Verify transaction");
                                                                                            otpRequest.put("userId", LoginManager.getUser().getId());

                                                                                            // Call api generate OTP
                                                                                            ApiService apiGenarateOTP = ApiClient.getClient(ApiConfig.getOTPBaseURL()).create(ApiService.class);
                                                                                            Call<Map<String, String>> callApiGenarateOTP = apiGenarateOTP.genarateOTP(otpRequest);
                                                                                            callApiGenarateOTP.enqueue(new Callback<Map<String, String>>() {
                                                                                                @Override
                                                                                                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                                                                                    if (response.isSuccessful() && response.body() != null) {
                                                                                                        Log.d("Call api generate and send otp: ", "Success");

                                                                                                        Map<String, String> otpResponse = response.body();
                                                                                                        Log.d("OTP response: ", otpResponse.get("otp") + "-" + otpResponse.get("message"));

                                                                                                        Intent intent = new Intent(view.getContext(), VerifyTransaction.class);
                                                                                                        intent.putExtra("transactionId", newTransaction.getTransactionId());
                                                                                                        intent.putExtra("paymentId", newPayment.getId());
                                                                                                        activity.startActivity(intent);

                                                                                                        // Show dialog enter otp
//                                                                                          showOTPDialog(view.getContext(), holder, tuition, newPayment, newTransaction);
                                                                                                    }
                                                                                                    else {
                                                                                                        // khong call api tao otp duoc bao failed
                                                                                                        TransactionProcessing.updateTransactionStatus(newTransaction,"FAILED");
                                                                                                        TransactionProcessing.updatePaymentStatus(newPayment, "FAILED");
                                                                                                        Log.d("Call api generate and send otp: ", "Error");
                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                                                                                    // khong call api tao otp duoc bao failed
                                                                                                    TransactionProcessing.updateTransactionStatus(newTransaction, "FAILED");
                                                                                                    TransactionProcessing.updatePaymentStatus(newPayment, "FAILED");
                                                                                                    t.printStackTrace();
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                        else {
                                                                                            // create payment khong thanh cong thi huy giao dich
                                                                                            TransactionProcessing.updateTransactionStatus(newTransaction, "FAILED");
                                                                                            Log.d("Call api create payment", "Error");
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<Payment> call, Throwable t) {
                                                                                        // create payment khong thanh cong thi huy giao dich
                                                                                        TransactionProcessing.updateTransactionStatus(newTransaction, "FAILED");
                                                                                        t.printStackTrace();
                                                                                    }
                                                                                });
                                                                            }
                                                                            else {
                                                                                // khong kiem tra duoc so du thi huy giao dich
                                                                                TransactionProcessing.updateTransactionStatus(newTransaction, "FAILED");
                                                                                Log.d("Call api get balance: ", "Error");
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Double> call, Throwable t) {
                                                                            t.printStackTrace();
                                                                        }
                                                                    });
                                                                }
                                                                else {
                                                                    Log.d("Call api create transaction: ", "Error");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Transaction> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        Log.d("Call api check active user: ", "Failed");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                    t.printStackTrace();
                                                }
                                            });
                                        }
                                        else {
                                            Log.d("Call api update tuitions: ", "Error");
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
                            Log.d("Call api get tuition: ", "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<Tuition> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return tuitions.size();
    }

    private void showButtonPayment(TuitionHolder holder) {
        holder.btPayment.setEnabled(true);
        holder.btPayment.setText("Payment");
        holder.progressLoading.setVisibility(View.GONE);
    }

    private TransactionRequest createTransactionRequest(int tuitionId, String studentId, double amount) {
        return new TransactionRequest(
                LoginManager.getUser().getId(),
                tuitionId,
                studentId,
                new Date(),
                amount,
                "PAYMENT",
                "PENDING"
        );
    }

    private PaymentRequest createPaymentRequest(int tuitionId, double amount, int transactionId) {
        return new PaymentRequest(LoginManager.getUser().getId(), tuitionId, "CK", amount, transactionId);
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
}
