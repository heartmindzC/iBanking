package com.example.userservice.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity //Xem class này là một thực thể trong JPA/Hibernate
@Table(name = "users") //ánh xạ tới bảng "user" trong database
@Data
@NoArgsConstructor //auto-gen constructor không tham số
@AllArgsConstructor //auto-gen constructor full tham số

public class User {
    @Id //đánh dấu biến này là một id làm primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-gen tăng dần
    private Integer id;

    @Column(name = "student_id",nullable = false, unique = true) // đánh dấu ràng buộc không được null và KHÔNG thể trùng lặp
    private String studentId;

    @Column(nullable = false, unique = false) // đánh dấu ràng buộc không được null và có thể trùng lặp
    private String name;

    @Column(nullable = false, unique = false) // đánh dấu ràng buộc không được null và có thể trùng lặp
    private String password;

    @Column(name = "birth_date", nullable = false, unique = false) // đánh dấu ràng buộc không được null và có thể trùng lặp, ánh xạ cột lại vì khác tên trong database (birth_date)
    private Date birthDate;

    @Column(nullable = false, unique = false) // đánh dấu ràng buộc không được null và có thể trùng lặp
    private String gender;

    @Column(nullable = false, unique = false) // đánh dấu ràng buộc không được null và có thể trùng lặp
    private String classes;

    @Column(nullable = false, unique = false) // đánh dấu ràng buộc không được null và có thể trùng lặp
    private String email;

    @Column(nullable = false, unique = false)
    private Boolean active = false;
}
