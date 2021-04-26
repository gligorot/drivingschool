package com.example.drivingschool.repository;

import com.example.drivingschool.model.TrainingStateTransition;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingStateTransitionRepository extends JpaRepository<TrainingStateTransition, Long> {

    List<TrainingStateTransition> findAllByTrainingId(Long trainingId);

    List<TrainingStateTransition> findAllByToState(TrainingStateName state);

}
