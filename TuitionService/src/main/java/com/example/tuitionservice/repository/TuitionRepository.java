package com.example.tuitionservice.repository;

import com.example.tuitionservice.model.Tuition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TuitionRepository extends JpaRepository<Tuition,Integer> {
    Optional<Tuition> findTuitionById(int id);
    Optional<Tuition> findTuitionByUserId(int userId);
}
