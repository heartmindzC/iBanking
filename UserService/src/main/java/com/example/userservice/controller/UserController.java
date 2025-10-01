package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable  Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/userByStudentId/{studentId}")
    public ResponseEntity<User> getUserByStudentId(@PathVariable  String studentId){
        return ResponseEntity.ok(userService.getUserByStudentId(studentId));
    }

}
