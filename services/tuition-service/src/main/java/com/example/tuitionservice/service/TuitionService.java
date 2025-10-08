package com.example.tuitionservice.service;


import com.example.tuitionservice.model.Tuition;
import com.example.tuitionservice.repository.TuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Tuition> getTuitionByUserId(int userId) {
        List<Tuition> tuitions = tuitionRepository.findTuitionByUserId(userId);
        if(tuitions.isEmpty()){
            throw new RuntimeException("No tuitions found for User ID:"+userId);
        }
        return tuitions;
    }
}
