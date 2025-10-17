package com.example.tuitionservice.controller;


import com.example.tuitionservice.model.Tuition;
import com.example.tuitionservice.service.TuitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import java.util.List;

@RestController
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
    @PutMapping("/updateTuitionIsPaidById/{id}/{paid}")
    public ResponseEntity<String> updateTuitionIsPaidById(@PathVariable int id, @PathVariable boolean paid){
        tuitionService.updateTuitionIsPaidById(id, paid);
        return ResponseEntity.ok("Tuition["+id+"] updated successfully, current paid status is "+paid);
    }
}
