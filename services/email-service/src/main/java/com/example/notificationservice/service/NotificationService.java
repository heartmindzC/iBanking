package com.example.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class NotificationService {
    @Autowired
    private JavaMailSender mailSender;

    // -----------------------------------------------------------
    // 🔹 1️⃣ GỬI EMAIL OTP
    // -----------------------------------------------------------
    public void sendOtpEmail(String toEmail, String otpCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("iBanking - Mã OTP Xác Minh Của Bạn");

        String htmlContent = String.format("""
            <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px;">
                <div style="max-width: 480px; margin: auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 8px rgba(0,0,0,0.1); overflow: hidden;">
                    <div style="background-color: #051178; padding: 20px; text-align: center;">
                        <h1 style="color: white; margin: 0;">iBanking</h1>
                        <h2 style="color: white; margin: 0;">Mã OTP Xác Minh</h2>
                    </div>
                    <div style="padding: 30px; text-align: center;">
                        <p style="color: #333; font-size: 16px;">Đây là mã OTP xác minh của bạn:</p>
                        <h1 style="font-size: 40px; letter-spacing: 8px; color: #051178; margin: 20px 0;">%s</h1>
                        <p style="color: #666; font-size: 14px;">Mã này sẽ hết hạn trong 2 phút. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>
                    </div>
                    <div style="background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 13px; color: #888;">
                        Nếu bạn không yêu cầu mã này, hãy liên hệ ngay với chúng tôi để bảo vệ tài khoản của bạn.<br><br>
                        <strong>© 2025 iBanking Inc.</strong> • Chính Sách Bảo Mật • Điều Khoản Sử Dụng
                    </div>
                </div>
            </div>
            """, otpCode);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    // -----------------------------------------------------------
    // 🔹 2️⃣ GỬI EMAIL XÁC NHẬN GIAO DỊCH
    // -----------------------------------------------------------
    public void sendTransactionEmail(String toEmail, String transactionId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("iBanking - Giao Dịch Thành Công");

        String htmlContent = String.format("""
            <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px;">
                <div style="max-width: 480px; margin: auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 8px rgba(0,0,0,0.1); overflow: hidden;">
                    <div style="background-color: #051178; padding: 20px; text-align: center;">
                        <h1 style="color: white; margin: 0;">iBanking</h1>
                        <h2 style="color: white; margin: 0;">Giao Dịch Thành Công</h2>
                    </div>
                    <div style="padding: 30px; text-align: center;">
                        <p style="color: #333; font-size: 16px;">Mã giao dịch:</p>
                        <h1 style="font-size: 40px; letter-spacing: 8px; color: #051178; margin: 20px 0;">%s</h1>
                    </div>
                    <div style="background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 13px; color: #888;">
                        <strong>© 2025 iBanking Inc.</strong> • Chính Sách Bảo Mật • Điều Khoản Sử Dụng
                    </div>
                </div>
            </div>
            """, transactionId);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
