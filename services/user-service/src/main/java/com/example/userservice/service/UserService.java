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
        if(user.isPresent()) {
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
    public Boolean getUserActiveStatusByUserId(Integer id){
        return userRepository.getUserActiveStatusByUserId(id);
    }
    public String getEmailByUserId(Integer id){
        return userRepository.findById(id).get().getEmail();
    }
    public String updateActiveStatusByUserId(Integer id, Boolean active){
        Optional<User> user = userRepository.findById(id);
        user.get().setActive(active);
        userRepository.save(user.get());
        return "Update successful";
    }
    public User login(String studentId, String password) {
        Optional<User> user = userRepository.findByStudentId(studentId);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user.get();
            } else {
                throw new RuntimeException("Sai mật khẩu");
            }
        } else {
            throw new RuntimeException("Không tìm thấy sinh viên ID: " + studentId);
        }
    }

}
