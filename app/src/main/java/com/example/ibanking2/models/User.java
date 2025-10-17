package com.example.ibanking2.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;
import java.util.Date;

public class User {
    @SerializedName("id")
    private int id;

    @SerializedName("studentId")
    private String studentId;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("birthDate")
    private Date birthDate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("classes")
    private String classes;

    @SerializedName("email")
    private String email;

    public User(int id, String studentId, String name, String password, Date birthDate, String gender, String classes) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.classes = classes;
    }

    public User(int id, String studentId, String name, String password, Date birthDate, String gender, String classes, String email) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.classes = classes;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
