package com.example.ibanking2.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.R;
import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.LoginManager;
import com.example.ibanking2.models.Payment;
import com.example.ibanking2.models.PaymentRequest;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.TransactionRequest;
import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuitionAdapter extends RecyclerView.Adapter<TuitionHolder> {
    private List<Tuition> tuitions;
    private User user;

    // Khi click nut tim se truyen se call api tim user sau do truyen vao adapter
    public TuitionAdapter(List<Tuition> tuitions, User user) {
        this.tuitions = tuitions;
        this.user = user;
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
        holder.tvDateOfBirth.setText(user.getBirthDate().toString());
        holder.tvClass.setText(user.getClasses());
        holder.tvAmount.setText("" + tuition.getAmount());
        holder.tvStatus.setText(String.valueOf(tuition.isPaid()));

        // gach hoc phi va an nut thanh toan
        if (tuition.isPaid()) {
            holder.btPayment.setVisibility(View.GONE);
            holder.tvAmount.setPaintFlags(holder.tvAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        //
        holder.btPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call api thanh toan hoc phi
                holder.btPayment.setEnabled(false);
                holder.btPayment.setText("Đang xử lí...");
                holder.progressLoading.setVisibility(View.VISIBLE);

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

                                        if (balance < tuition.getAmount()) {
                                            Log.d("Notification: ", "Not enough balance");
                                            Toast.makeText(view.getContext(), "So du khong du", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        PaymentRequest paymentRequest = createPaymentRequest(tuition.getId(), tuition.getAmount(), newTransaction.getTransactionId());

                                        ApiService apiCreatePayment = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
                                        Call<Payment> callApiCreatePayment = apiCreatePayment.createPayment(paymentRequest);
                                        callApiCreatePayment.enqueue(new Callback<Payment>() {
                                            @Override
                                            public void onResponse(Call<Payment> call, Response<Payment> response) {
                                                if (response.isSuccessful() && response.body() != null) {
                                                    Payment newPayment = response.body();

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

                                                                // Show dialog enterotp
                                                                showOTPDialog(view.getContext(), holder);
                                                            }
                                                            else {
                                                                Log.d("Call api generate and send otp: ", "Error");
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                                            t.printStackTrace();
                                                        }
                                                    });

                                                }
                                                else {
                                                    Log.d("Call api create payment", "Error");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Payment> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
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
        });
    }

    @Override
    public int getItemCount() {
        return tuitions.size();
    }

    private TransactionRequest createTransactionRequest(int tuitionId, String studentId, double amount) {
        return new TransactionRequest(
                LoginManager.getUser().getId(),
                tuitionId,
                studentId,
                new Date(),
                amount,
                "PAYMENT",
                "false"
        );
    }

    private PaymentRequest createPaymentRequest(int tuitionId, double amount, int transactionId) {
        return new PaymentRequest(LoginManager.getUser().getId(), tuitionId, "CK", amount, transactionId);
    }

    private void showOTPDialog(Context context, TuitionHolder holder) {
        final EditText inputOtp = new EditText(context);
        inputOtp.setHint("Nhập mã OTP");
        inputOtp.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputOtp.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(6) }); // giới hạn 6 chữ số

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Xác thực OTP")
                .setMessage("Nhập mã OTP gồm 6 chữ số")
                .setView(inputOtp)
                .setPositiveButton("Xác nhận", (dialogInterface, which) -> {
                    String otp = inputOtp.getText().toString().trim();
                    if (otp.length() == 6) {
                        Toast.makeText(context, "OTP: " + otp, Toast.LENGTH_SHORT).show();
                        // TODO: Gọi API xác thực OTP ở đây

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
                                        Toast.makeText(context, "Nhap ma OTP dung", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context, "Nhap ma OTP sai", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Log.d("Call api verify OTP", "Error");
                                }
                            }

                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                    } else {
                        Toast.makeText(context, "Mã OTP phải đủ 6 chữ số", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialogInterface, i) -> {
                    holder.btPayment.setEnabled(true);
                    holder.btPayment.setText("Payment");
                    holder.progressLoading.setVisibility(View.GONE);
                })
                .create();

        dialog.show();
    }

    private void processTransaction() {

    }
}
