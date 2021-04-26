package com.example.drivingschool.service;

import com.example.drivingschool.model.Candidate;
import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.TrainingCategory;
import com.example.drivingschool.model.dto.TrainingDTO;
import com.example.drivingschool.model.dto.TrainingDetailsDTO;

import java.util.List;

public interface TrainingService {

    Training getById(Long trainingId);

    List<TrainingDTO> getAllForInstructorWithDetails(Instructor instructor);

    List<TrainingDTO> getAllForCandidateWithDetails(Candidate candidate);

    Training createNewTraining(Instructor instructor, TrainingCategory category, Candidate candidate);

}
