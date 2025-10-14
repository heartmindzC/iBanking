package com.example.otpservice.service;

import com.example.otpservice.model.OTP;
import com.example.otpservice.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailService emailService;
    
    private final int EXPIRATION_MINUTES = 2;

    public String generateOtp(Integer userId, String purpose, String email) {
        String code = String.format("%06d", new Random().nextInt(999999)); // 6 số ngẫu nhiên

        OTP otp = new OTP();
        otp.setUserId(userId);
        otp.setCode(code);
        otp.setPurpose(purpose);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));

        otpRepository.save(otp);

        if(email != null){
            emailService.sendOtpEmail(email, code);
        }
        
        return code;
    }

    public boolean verifyOtp(Integer userId, String purpose, String code) {
        Optional<OTP> otpOpt = otpRepository.findTopByUserIdAndPurposeOrderByCreatedAtDesc(userId, purpose);

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
