package com.example.otpservice.repository;

import com.example.otpservice.model.OTP;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OtpRepository extends JpaRepository<OTP,Integer> {
    Optional<OTP> findTopByUserIdAndPurposeOrderByCreatedAtDesc(String username, String purpose);
}
