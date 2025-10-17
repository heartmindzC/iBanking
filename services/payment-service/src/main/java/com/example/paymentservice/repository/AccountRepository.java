package com.example.paymentservice.repository;

import com.example.paymentservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    Account findAccountByUserId(int userId);
    
    @Query("SELECT a.balance FROM Account a WHERE a.userId = :userId")
    Double findBalanceByUserId(@Param("userId") int userId);
    
    @Modifying
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.userId = :userId")
    int updateBalanceByUserId(@Param("userId") int userId, @Param("balance") Double balance);
}
