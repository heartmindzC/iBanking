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

    @Column(name="user_id", nullable = false, unique = false)
    private int userId;

    @Column(nullable = false, unique = false)
    private double amount;

    @Column(nullable = false, unique = false)
    private Date date;

    @Column(name ="is_paid",nullable = false, unique = false)
//    @JsonProperty("paid")
    private boolean isPaid;

}
