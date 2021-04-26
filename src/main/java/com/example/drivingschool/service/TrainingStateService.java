package com.example.drivingschool.service;

import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.example.drivingschool.model.trainingstates.TrainingState;
import com.example.drivingschool.model.trainingstates.TrainingStateTheory;

public interface TrainingStateService {

    Integer getCountForTrainingWithLatestState(Training training, TrainingStateName latestStateName);

    void increaseStatusCountForTrainingWithLatestState(Training training, TrainingStateName latestStateName);

    TrainingStateTheory initializeTrainingState(Training training);

    void removeFromCurrentStateTable(Training training, TrainingStateName currentStateName);

    TrainingState addToNextStateTable(Training training, TrainingStateName currentStateName);
}
