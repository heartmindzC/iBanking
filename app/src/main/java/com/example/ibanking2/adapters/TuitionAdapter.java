package com.example.ibanking2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.R;
import com.example.ibanking2.api.ApiClient;
import com.example.ibanking2.api.ApiConfig;
import com.example.ibanking2.api.ApiService;
import com.example.ibanking2.models.Transaction;
import com.example.ibanking2.models.Tuition;
import com.example.ibanking2.models.User;

import java.util.List;

import retrofit2.Call;

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

        //
        holder.btPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call api thanh toan hoc phi

                // Tao mot new transaction
//                Transaction newTransaction = new Transaction()

                // call api tao giao dich
//                ApiService api = ApiClient.getClient(ApiConfig.getTransactionBaseURL()).create(ApiService.class);
//                Call<Transaction> callTransaction = api.createTransaction(newTransaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tuitions.size();
    }
}
