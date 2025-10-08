package com.example.otpservice.service;

import com.example.otpservice.model.OTP;
import com.example.otpservice.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    private final int EXPIRATION_MINUTES = 5;

    public String generateOtp(String username, String purpose) {
        String code = String.format("%06d", new Random().nextInt(999999)); // 6 số ngẫu nhiên

        OTP otp = new OTP();
        otp.setUsername(username);
        otp.setCode(code);
        otp.setPurpose(purpose);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));

        otpRepository.save(otp);
        return code;
    }

    public boolean verifyOtp(String username, String purpose, String code) {
        Optional<OTP> otpOpt = otpRepository.findTopByUserIdAndPurposeOrderByCreatedAtDesc(username, purpose);

        if (otpOpt.isEmpty()) return false;

        OTP otp = otpOpt.get();

        if (otp.getVerified()) return false;
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) return false;
        if (!otp.getCode().equals(code)) return false;

        otp.setVerified(true);
        otpRepository.save(otp);
        return true;
    }
}
