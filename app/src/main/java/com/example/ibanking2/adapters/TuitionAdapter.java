package com.example.ibanking2.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.ibanking2.PaymentSuccess;
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
                holder.progressLoading.setVisibility(View.VISIBLE);   // hieu ung click nut

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
                                            updateStatusTransaction(newTransaction, "FAILED");
                                            Log.d("Notification: ", "Not enough balance");
                                            Toast.makeText(view.getContext(), "So du khong du", Toast.LENGTH_SHORT).show();
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

                                                                // Show dialog enterotp
                                                                showOTPDialog(view.getContext(), holder, tuition, newPayment, newTransaction);
                                                            }
                                                            else {
                                                                // khong call api tao otp duoc bao failed
                                                                updateStatusTransaction(newTransaction, "FAILED");
                                                                updateStatusPayment(newPayment, "FAILED");
                                                                Log.d("Call api generate and send otp: ", "Error");
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                                            // khong call api tao otp duoc bao failed
                                                            updateStatusTransaction(newTransaction, "FAILED");
                                                            updateStatusPayment(newPayment, "FAILED");
                                                            t.printStackTrace();
                                                        }
                                                    });

                                                }
                                                else {
                                                    // create payment khong thanh cong thi huy giao dich
                                                    updateStatusTransaction(newTransaction, "FAILED");
                                                    Log.d("Call api create payment", "Error");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Payment> call, Throwable t) {
                                                // create payment khong thanh cong thi huy giao dich
                                                updateStatusTransaction(newTransaction, "FAILED");
                                                t.printStackTrace();
                                            }
                                        });
                                    }
                                    else {
                                        // khong kiem tra duoc so du thi huy giao dich
                                        updateStatusTransaction(newTransaction, "FAILED");
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
                "PENDING"
        );
    }

    private PaymentRequest createPaymentRequest(int tuitionId, double amount, int transactionId) {
        return new PaymentRequest(LoginManager.getUser().getId(), tuitionId, "CK", amount, transactionId);
    }

    private void showOTPDialog(Context context, TuitionHolder holder, Tuition tuition, Payment payment, Transaction transaction) {
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

                                        // xu li sau khi nhap otp dung
                                        updateBalance(tuition);
                                        updateStatusPayment(payment, "SUCCESS");
                                        updateStatusTransaction(transaction, "SUCCESS");
                                        updateStatusTuition(tuition, true);
                                        sendEmailVerifySuccess(transaction);

                                        Intent intent = new Intent(context, PaymentSuccess.class);
                                        context.startActivity(intent);
                                        activity.finish();
                                    }
                                    else {
                                        updateStatusTransaction(transaction, "FAILED");
                                        updateStatusPayment(payment, "CANCEL");
                                        Toast.makeText(context, "Nhap ma OTP sai", Toast.LENGTH_SHORT).show();
                                        showButtonPayment(holder);
                                    }
                                }
                                else {
                                    updateStatusTransaction(transaction, "FAILED");
                                    updateStatusPayment(payment, "FAILED");

                                    Log.d("Call api verify OTP", "Error");
                                    showButtonPayment(holder);
                                }
                            }

                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                // khong xac thu otp duoc
                                updateStatusTransaction(transaction, "FAILED");
                                updateStatusPayment(payment, "FAILED");

                                t.printStackTrace();
                                showButtonPayment(holder);
                            }
                        });

                    } else {
                        Toast.makeText(context, "Mã OTP phải đủ 6 chữ số", Toast.LENGTH_SHORT).show();
                        showButtonPayment(holder);
                    }
                })
                .setNegativeButton("Hủy", (dialogInterface, i) -> {
                    updateStatusTransaction(transaction, "FAILED");
                    updateStatusPayment(payment, "CANCEL");
                    showButtonPayment(holder);
                })
                .create();

        dialog.show();
    }

    private void showButtonPayment(TuitionHolder holder) {
        holder.btPayment.setEnabled(true);
        holder.btPayment.setText("Payment");
        holder.progressLoading.setVisibility(View.GONE);
    }

    private void updateBalance(Tuition tuition) {
        // Call lai api check so du va tru tien
        ApiService apiUpdateBalance = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
        Call<Double> callApiGetBalance = apiUpdateBalance.getBalanceByUserId(LoginManager.getUser().getId());
        callApiGetBalance.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api get balance: ", "Success");
                    Double balance = response.body();
                    if (balance > tuition.getAmount()) {
                        Double newBalance = balance - tuition.getAmount();

                        // Call api update new balance
                        Call<ResponseBody> callApiUpdateBalance = apiUpdateBalance.updateBalance(LoginManager.getUser().getId(), newBalance);
                        callApiUpdateBalance.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Log.d("Call Api update balance: ", "Success");
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

    // xu li update transaction
    private void updateStatusTransaction(Transaction transaction, String status){
        transaction.setStatus(status);

        ApiService apiUpdateTransaction = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
        Call<Transaction> callApiUpdateTransaction = apiUpdateTransaction.updateTransaction(transaction.getTransactionId(), transaction);
        callApiUpdateTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api update transaction: ", "Success");
                    Log.d("Call api update transaction: ", response.body().toString());
                }
                else {
                    Log.d("Call api update transaction: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // xu li update payment
    private void updateStatusPayment(Payment payment, String status) {
        payment.setStatus(status);

        ApiService apiUpdatePayment = ApiClient.getClient(ApiConfig.getPaymentBaseURL()).create(ApiService.class);
        Log.d("Payment: ", payment.getId() + "");
        Call<Payment> callApiUpdatePayment = apiUpdatePayment.updatePayment(payment.getId(), payment.getStatus());
        callApiUpdatePayment.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call api update Payment: ", "Success");
//                    Log.d("Call api update Payment: ", response.body().toString());
                }
                else {
                    Log.d("Call api update Payment: ", "Error code: " + response.code() +
                            " | body: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // xu li gui email khi giao dich thanh cong
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

    // xu li update is_paid trong tuition
    private void updateStatusTuition(Tuition tuition, boolean status){
        ApiService apiUpdateTuitionIsPaid = ApiClient.getClient(ApiConfig.getTuitionBaseURL()).create(ApiService.class);
        Call<ResponseBody> callApiUpdateTuitionIsPaid = apiUpdateTuitionIsPaid.updateTuitionIsPaid(tuition.getId(), status);
        callApiUpdateTuitionIsPaid.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Call Api update tuition is_paid: ", "Success");
                }
                else {
                    Log.d("Call Api update tuition is_paid: ", "Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
