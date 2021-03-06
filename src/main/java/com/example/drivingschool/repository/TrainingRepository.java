package com.example.drivingschool.repository;

import com.example.drivingschool.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByInstructorId(Long instructorId);

    List<Training> findAllByCandidateId(Long candidateId);
}
