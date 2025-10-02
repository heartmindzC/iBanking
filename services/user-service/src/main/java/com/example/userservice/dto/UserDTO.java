package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String studentId;
    private String name;
    private Date birthDate;
    private String gender;
    private String classes;
}
