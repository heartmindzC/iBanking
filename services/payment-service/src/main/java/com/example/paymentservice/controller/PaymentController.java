package com.example.paymentservice.controller;


import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return paymentService.getPaymentByTuitionId(tuitionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getPaymentByUserId/{userId}")
    public ResponseEntity<Payment> getPaymentByUserId(@PathVariable int userId) {
        return paymentService.getPaymentByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getBalanceByUserId/{userId}")
    public ResponseEntity<Double> getBalanceByUserId(@PathVariable int userId) {
        try {
            Double balance = paymentService.getBalanceByUserId(userId);
            if (balance == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // 📋 3️⃣ Lấy danh sách tất cả payment (cho admin hoặc kế toán)
    @GetMapping("/getAllPayments")
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
    @PutMapping("/updateBalance/{userId}/{updBalance}")
    public ResponseEntity<String> updateBalance(@PathVariable int userId, @PathVariable Double updBalance) {
        try {
            // Validation
            if (userId <= 0) {
                return ResponseEntity.badRequest().body("Invalid user ID: " + userId);
            }
            if (updBalance == null || updBalance < 0) {
                return ResponseEntity.badRequest().body("Invalid balance: " + updBalance + ". Balance must be non-negative.");
            }
            
            paymentService.updateBalanceByUserId(userId, updBalance);
            return ResponseEntity.ok("Balance updated successfully (User ID: " + userId + ", New Balance: " + updBalance + ")");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    // // 💰 6️⃣ Lấy số dư tài khoản theo User ID
    // @GetMapping("/getBalanceByUserId/{userId}")
    // public ResponseEntity<Double> getBalanceByUserId(@PathVariable int userId) {
    //     double balance = paymentService.getBalanceByUserId(userId);
    //     return ResponseEntity.ok(balance);
    // }
    // ❌ 7️⃣ Xóa payment (thường chỉ dùng trong admin panel)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
