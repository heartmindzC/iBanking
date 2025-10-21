package com.example.ibanking2.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.R;

public class TransactionHolder extends RecyclerView.ViewHolder {

    TextView tvTransactionContent, tvAmount, tvDate, tvStatus;

    public TransactionHolder(@NonNull View itemView) {
        super(itemView);
        tvTransactionContent = itemView.findViewById(R.id.tvTransactionContent);
        tvAmount = itemView.findViewById(R.id.tvAmount);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvStatus = itemView.findViewById(R.id.tvStatus);
    }
}
