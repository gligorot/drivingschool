package com.example.drivingschool.model.dto;

import com.example.drivingschool.model.Candidate;
import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.TrainingCategory;
import lombok.Data;

@Data
public class TrainingDTO {
    private Long id;
    private Instructor instructor;
    private TrainingCategory category;
    private Candidate candidate;
    private TrainingDetailsDTO trainingDetailsDTO;

    public TrainingDTO() {
    }

    public TrainingDTO(Training training) {
        this.id = training.getId();
        this.instructor = training.getInstructor();
        this.category = training.getCategory();
        this.candidate = training.getCandidate();
    }

    public TrainingDTO(Training training, TrainingDetailsDTO trainingDetailsDTO) {
        this.id = training.getId();
        this.instructor = training.getInstructor();
        this.category = training.getCategory();
        this.candidate = training.getCandidate();
        this.trainingDetailsDTO = trainingDetailsDTO;
    }
}
