package com.example.drivingschool.model;

import com.example.drivingschool.model.enumerations.TrainingStateName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id")
    private TrainingCategory category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Training() {}

    public Training(Instructor instructor, TrainingCategory category, Candidate candidate) {
        this.instructor = instructor;
        this.category = category;
        this.candidate = candidate;
    }

    public boolean isPromotable(TrainingStateName currentState, Integer currentStateCount) {

        if (currentState.equals(TrainingStateName.THEORY))
            return currentStateCount >= category.getRequiredTheoryTrainingDays();
        if (currentState.equals(TrainingStateName.PRACTICE))
            return currentStateCount >= category.getRequiredPracticeTrainingDays();
        if (currentState.equals(TrainingStateName.CITY_TEST))
            return false; // can't promote further than this
        else
            return currentStateCount >= 1; // TODO maybe add checks for other stuff too
    }
}
