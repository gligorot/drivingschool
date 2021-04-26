package com.example.drivingschool.service;

import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.TrainingStateTransition;

import java.util.List;

public interface TrainingStateTransitionService {

    List<TrainingStateTransition> findAllForTraining(Training training);

    TrainingStateTransition promoteTrainingToNextState(Training training) throws Exception;

    TrainingStateTransition getLatestFrom(List<TrainingStateTransition> stateTransitions);

    TrainingStateTransition initiateTrainingState(Training training);
}
