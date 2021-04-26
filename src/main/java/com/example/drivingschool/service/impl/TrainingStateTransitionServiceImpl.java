package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.TrainingCategory;
import com.example.drivingschool.model.TrainingStateTransition;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.example.drivingschool.repository.TrainingRepository;
import com.example.drivingschool.repository.TrainingStateTransitionRepository;
import com.example.drivingschool.service.TrainingService;
import com.example.drivingschool.service.TrainingStateService;
import com.example.drivingschool.service.TrainingStateTransitionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class TrainingStateTransitionServiceImpl implements TrainingStateTransitionService {

    private final TrainingStateTransitionRepository transitionRepository;
    private final TrainingStateService transitionService;
    private final TrainingRepository trainingRepository; // TODO temp - just for testing purposes

    public TrainingStateTransitionServiceImpl(TrainingStateTransitionRepository transitionRepository, TrainingStateService transitionService, TrainingRepository trainingRepository) {
        this.transitionRepository = transitionRepository;
        this.transitionService = transitionService;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<TrainingStateTransition> findAllForTraining(Training training) {
        return transitionRepository.findAllByTrainingId(training.getId());
    }

    @Override
    @Transactional
    public TrainingStateTransition promoteTrainingToNextState(Training training) throws Exception {

        List<TrainingStateTransition> stateTransitions = findAllForTraining(training);
        TrainingStateTransition latestTransition = getLatestFrom(stateTransitions);
        TrainingStateName latestStateName = latestTransition.getToState(); // smelly but can't bother now

        if(isPromotable(training, latestStateName)) {
            return moveTrainingToNextState(training, latestStateName);
        } else {
            throw new Exception("Selected training for candidate is not valid for promotion!");
        }
    }

    @Override
    public TrainingStateTransition getLatestFrom(List<TrainingStateTransition> stateTransitions) {
        return stateTransitions.stream()
                .max(Comparator.comparing(TrainingStateTransition::getTimestamp))
                .orElseThrow();
    }

    @Override
    public TrainingStateTransition initiateTrainingState(Training training) {
        transitionService.initializeTrainingState(training);

        TrainingStateTransition initialTransition =
                new TrainingStateTransition(training, null, TrainingStateName.THEORY);
        return transitionRepository.save(initialTransition);
    }

    private boolean isPromotable(Training training, TrainingStateName latestStateName) {
        Integer latestStateStatusCount = transitionService.getCountForTrainingWithLatestState(training, latestStateName);
        TrainingCategory category = training.getCategory();

        if (latestStateName.equals(TrainingStateName.THEORY))
            return latestStateStatusCount >= category.getRequiredTheoryTrainingDays();
        if (latestStateName.equals(TrainingStateName.PRACTICE))
            return latestStateStatusCount >= category.getRequiredPracticeTrainingDays();
        if (latestStateName.equals(TrainingStateName.CITY_TEST))
            return false; // can't promote further than this
        else
            return latestStateStatusCount >= 1; // TODO maybe add checks for other stuff too
    };

    // protected (not private) bc of @Transactional
//    @Transactional
    protected TrainingStateTransition moveTrainingToNextState(Training training, TrainingStateName currentStateName) {
        TrainingStateName nextStateName = determineNextTrainingStateName(currentStateName);
        TrainingStateTransition promotionTransition =
                new TrainingStateTransition(training, currentStateName, nextStateName);

        transitionService.removeFromCurrentStateTable(training, currentStateName);
        transitionService.addToNextStateTable(training, currentStateName);

        return transitionRepository.save(promotionTransition);
    }

    private TrainingStateName determineNextTrainingStateName(TrainingStateName currentStateName) {
        switch(currentStateName) {
            case THEORY:
                return TrainingStateName.PRACTICE;
            case PRACTICE:
                return TrainingStateName.THEORY_TEST;
            case THEORY_TEST:
                return TrainingStateName.RANGE_TEST;
            case RANGE_TEST:
                return TrainingStateName.CITY_TEST;
            default:
                return null; // temp smelly code, program shouldn't get here anyway
        }
    }
}
