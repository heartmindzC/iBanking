package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        else {
            throw new RuntimeException("User(ID:"+id+") not found");
        }
    }
    public User getUserByStudentId(String studentId){
        Optional<User> user = userRepository.findByStudentId(studentId);
        if(user.isPresent()){
            return user.get();
        }
        else {
            throw new RuntimeException("Student(ID:"+studentId+") not found");
        }
    }
}
