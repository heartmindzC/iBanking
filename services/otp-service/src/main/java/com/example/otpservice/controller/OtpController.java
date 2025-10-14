package com.example.otpservice.controller;


import com.example.otpservice.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public Map<String, String> generateOtp(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        String purpose = (String) request.get("purpose");

        String code = otpService.generateOtp(userId, purpose);
        return Map.of("message", "OTP generated successfully", "otp", code);
    }

    @PostMapping("/verify")
    public Map<String, String> verifyOtp(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        String purpose = (String) request.get("purpose");
        String code = (String) request.get("code");

        boolean result = otpService.verifyOtp(userId, purpose, code);

        return Map.of("verified", String.valueOf(result));
    }
}
