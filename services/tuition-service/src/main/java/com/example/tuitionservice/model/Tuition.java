package com.example.tuitionservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "tuitions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_id", nullable = false, unique = true)
    private int userId; //Get from studentId query về userId,đem userId qua get Tuition
    @Column(nullable = false, unique = false)
    private double amount;
    @Column(nullable = false, unique = false)
    private Date date;
    @Column(name ="is_paid",nullable = false, unique = false)
    private boolean isPaid;

}
