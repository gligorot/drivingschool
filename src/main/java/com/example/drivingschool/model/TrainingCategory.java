package com.example.drivingschool.model;

import com.example.drivingschool.model.enumerations.TrainingCategoryName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TrainingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer requiredTheoryTrainingDays;

    private Integer requiredPracticeTrainingDays;

    @Enumerated(EnumType.STRING)
    private TrainingCategoryName name;

    public TrainingCategory() {
    }

    public TrainingCategory(Integer requiredTheoryTrainingDays, Integer requiredPracticeTrainingDays, TrainingCategoryName name) {
        this.requiredTheoryTrainingDays = requiredTheoryTrainingDays;
        this.requiredPracticeTrainingDays = requiredPracticeTrainingDays;
        this.name = name;
    }
}
