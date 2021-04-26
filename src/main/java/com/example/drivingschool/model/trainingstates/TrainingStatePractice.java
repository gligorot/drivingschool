package com.example.drivingschool.model.trainingstates;

import com.example.drivingschool.model.Training;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "training_state_practice")
public class TrainingStatePractice implements TrainingState {

    @Id
    @Column(name = "training_id", insertable = false, updatable = false)
    private Long trainingId; // don't bother with getter/setter since the `training` reference handles everything

    @OneToOne(optional = false)
    @JoinColumn(name = "training_id")
    private Training training;

    private Integer daysCompleted;

    public TrainingStatePractice() {
    }

    public TrainingStatePractice(Training training) {
        this.training = training;
//        this.trainingId = training.getId(); // not supposed to be needed?
        this.daysCompleted = 0;
    }

    public TrainingStatePractice(Training training, Integer practiceDaysCompleted) {
        this.training = training;
        this.trainingId = training.getId(); // not supposed to be needed?
        this.daysCompleted = practiceDaysCompleted;
    }

    @Override
    public Integer getStateStatusCount() {
        return getDaysCompleted();
    }

    @Override
    public void incrementStateStatusCount() {
        setDaysCompleted(getStateStatusCount() + 1);
    }
}
