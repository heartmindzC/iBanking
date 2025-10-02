package com.example.tuitionservice.service;


import com.example.tuitionservice.model.Tuition;
import com.example.tuitionservice.repository.TuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TuitionService {
    @Autowired
    private TuitionRepository tuitionRepository;
    public Tuition getTuitionById(int id) {
        Optional<Tuition> tuition = tuitionRepository.findTuitionById(id);
        if(tuition.isPresent()){
            return tuition.get();
        }
        else {
            throw new RuntimeException("Tuition(ID:"+id+") not found");
        }
    }
    public Tuition getTuitionByUserId(int userId) {
        Optional<Tuition> tuition = tuitionRepository.findTuitionByUserId(userId);
        if(tuition.isPresent()){
            return tuition.get();
        }
        else {
            throw new RuntimeException("Tuition(User ID:"+userId+") not found");
        }
    }
}
