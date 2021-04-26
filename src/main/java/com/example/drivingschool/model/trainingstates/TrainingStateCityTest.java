package com.example.drivingschool.model.trainingstates;

import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.enumerations.TrainingTestStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "training_state_city_test")
public class TrainingStateCityTest implements TrainingState {

    @Id
    @Column(name = "training_id", insertable = false, updatable = false)
    private Long trainingId; // don't bother with getter/setter since the `training` reference handles everything

    @OneToOne(optional = false)
    @JoinColumn(name = "training_id")
    private Training training;

    private Integer tries;

    @Enumerated(EnumType.STRING)
    private TrainingTestStatus passStatus;

    public TrainingStateCityTest() {
    }

    public TrainingStateCityTest(Training training) {
        this.training = training;
        this.trainingId = training.getId(); // not supposed to be needed?
        this.tries = 0;
        this.passStatus = TrainingTestStatus.NOT_PASSED;
    }

    public TrainingStateCityTest(Training training, Integer testTries, TrainingTestStatus testPassStatus) {
        this.training = training;
        this.trainingId = training.getId(); // not supposed to be needed?
        this.tries = testTries;
        this.passStatus = testPassStatus;
    }

    @Override
    public Integer getStateStatusCount() {
        return getTries();
    }

    @Override
    public void incrementStateStatusCount() {
        setTries(getStateStatusCount() + 1);
    }

}
