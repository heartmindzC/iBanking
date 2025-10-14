package com.example.paymentservice.repository;

import com.example.paymentservice.model.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {
    
    Optional<PaymentAccount> findByUserId(int userId);
    
    @Query("SELECT p.balance FROM PaymentAccount p WHERE p.userId = :userId")
    Double findBalanceByUserId(@Param("userId") int userId);
    
    boolean existsByUserId(int userId);
}
