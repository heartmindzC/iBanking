package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @GetMapping("/getEmailByUserId/{userId}")
    public ResponseEntity<String> getEmailByUserId(@PathVariable  Integer userId){
        return ResponseEntity.ok(userService.getEmailByUserId(userId));
    }
    @GetMapping("/getActiveByUserId/{userId}")
    public ResponseEntity<Boolean> getActiveByUserId(@PathVariable  Integer userId){
        return ResponseEntity.ok(userService.getUserActiveStatusByUserId(userId));
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> request) {
        String studentId = request.get("studentId");
        String password = request.get("password");
        User user = userService.login(studentId, password);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/updateActiveByUserId/{userId}/{active}")
    public ResponseEntity updateActiveByUserId(@PathVariable  Integer userId, @PathVariable Boolean active){
        return ResponseEntity.ok(userService.updateActiveStatusByUserId(userId, active));
    }

}
