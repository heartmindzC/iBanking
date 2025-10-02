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

    private int userId; //Get from studentId query về userId,đem userId qua get Tuition

    private double amount;

    private Date date;

    private boolean isPaid;

}
