package com.example.tuitionservice.controller;


import com.example.tuitionservice.model.Tuition;
import com.example.tuitionservice.service.TuitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import java.util.List;

@Controller
@RequestMapping("/tuitions")
public class TuitionController {
    @Autowired
    private TuitionService tuitionService;
    @GetMapping("/getTuitionById/{id}")
    public ResponseEntity<Tuition> getTuitionById(@PathVariable int id){
        return ResponseEntity.ok(tuitionService.getTuitionById(id));
    }
    @GetMapping("/getTuitionByUserId/{userId}")
    public ResponseEntity<List<Tuition>> getTuitionByUserId(@PathVariable int userId){
        return ResponseEntity.ok(tuitionService.getTuitionByUserId(userId));
    }
}
