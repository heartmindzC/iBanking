package com.example.paymentservice.controller;


import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // 🧾 1️⃣ Tạo payment mới (khi sinh viên bắt đầu thanh toán)
    @PostMapping("/createPayment")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.ok(response);
    }

    // 🔍 2️⃣ Lấy chi tiết payment theo ID
    @GetMapping("/getPaymentById/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getPaymentByTuitionId/{tuitionId}")
    public ResponseEntity<Payment> getPaymentByTuitionId(@PathVariable int tuitionId) {
        return paymentService.getPaymentById(tuitionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getPaymentByUserId/{userId}")
    public ResponseEntity<Payment> getPaymentByUserId(@PathVariable int userId) {
        return paymentService.getPaymentById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 📋 3️⃣ Lấy danh sách tất cả payment (cho admin hoặc kế toán)
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // 🔄 4️⃣ Cập nhật trạng thái payment (VD: từ PENDING → SUCCESS)
    @PutMapping("/update/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable int id,
            @RequestParam PaymentStatus status) {

        Payment updated = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    // // 💰 6️⃣ Lấy số dư tài khoản theo User ID
    // @GetMapping("/getBalanceByUserId/{userId}")
    // public ResponseEntity<Double> getBalanceByUserId(@PathVariable int userId) {
    //     double balance = paymentService.getBalanceByUserId(userId);
    //     return ResponseEntity.ok(balance);
    // }
    @GetMapping("/getPaymentAccountByUserId/{userId}")
    public ResponseEntity<PaymentAccount> getPaymentAccountByUserId(@PathVariable int userId) {
        return paymentService.findPaymentAccountByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // ❌ 7️⃣ Xóa payment (thường chỉ dùng trong admin panel)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
