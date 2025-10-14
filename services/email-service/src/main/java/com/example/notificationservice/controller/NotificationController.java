package com.example.notificationservice.controller;

import com.example.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * Endpoint để gửi email OTP từ OTP service
     */
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> body) {
        try {
            notificationService.sendOtpEmail(body.get("toEmail"), body.get("otpCode"));
            return ResponseEntity.ok("OTP email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/send-transaction")
    public ResponseEntity<String> sendTransaction(@RequestBody Map<String, String> body) {
        try {
            notificationService.sendTransactionEmail(body.get("toEmail"), body.get("transactionId"));
            return ResponseEntity.ok("Transaction email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
