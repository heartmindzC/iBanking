package com.example.ibanking2.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibanking2.R;

public class TuitionHolder extends RecyclerView.ViewHolder {
    TextView tvStudentId, tvName, tvGender, tvClass, tvStatus, tvDateOfBirth, tvAmount;
    Button btPayment;
    public TuitionHolder(@NonNull View itemView) {
        super(itemView);
        tvStudentId = itemView.findViewById(R.id.tvStudentId);
        tvName = itemView.findViewById(R.id.tvName);
        tvGender = itemView.findViewById(R.id.tvGender);
        tvClass = itemView.findViewById(R.id.tvClass);
        tvStatus = itemView.findViewById(R.id.tvStatus);
        tvDateOfBirth = itemView.findViewById(R.id.tvDateOfBirth);
        tvAmount = itemView.findViewById(R.id.tvAmount);
        btPayment = itemView.findViewById(R.id.btPayment);
    }
}
