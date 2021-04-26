package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.example.drivingschool.model.trainingstates.*;
import com.example.drivingschool.repository.trainingstates.*;
import com.example.drivingschool.service.TrainingStateService;
import org.springframework.stereotype.Service;

@Service
public class TrainingStateServiceImpl implements TrainingStateService {

    // all the state repositories
    private final TrainingStateTheoryRepository theoryRepository;
    private final TrainingStatePracticeRepository practiceRepository;
    private final TrainingStateTheoryTestRepository theoryTestRepository;
    private final TrainingStateRangeTestRepository rangeTestRepository;
    private final TrainingStateCityTestRepository cityTestRepository;

    public TrainingStateServiceImpl(TrainingStateCityTestRepository cityTestRepository, TrainingStateTheoryTestRepository theoryTestRepository, TrainingStateRangeTestRepository rangeTestRepository, TrainingStateTheoryRepository theoryRepository, TrainingStatePracticeRepository practiceRepository) {
        this.cityTestRepository = cityTestRepository;
        this.theoryTestRepository = theoryTestRepository;
        this.rangeTestRepository = rangeTestRepository;
        this.theoryRepository = theoryRepository;
        this.practiceRepository = practiceRepository;
    }

    @Override
    public Integer getCountForTrainingWithLatestState(Training training, TrainingStateName latestStateName) {

        Long trainingId = training.getId();
        TrainingState latestState;
        // smelly code, but it has to exist go somewhere
        switch(latestStateName) {
            case THEORY:
                latestState = theoryRepository.getOne(trainingId); break;
            case PRACTICE:
                latestState = practiceRepository.getOne(trainingId); break;
            case THEORY_TEST:
                latestState = theoryTestRepository.getOne(trainingId); break;
            case RANGE_TEST:
                latestState = rangeTestRepository.getOne(trainingId); break;
            case CITY_TEST:
                latestState = cityTestRepository.getOne(trainingId); break;
            default:
                throw new Error("No valid state name defined");
        }

        return latestState.getStateStatusCount();
    }

    @Override
    public void increaseStatusCountForTrainingWithLatestState(Training training, TrainingStateName latestStateName) {
        Long trainingId = training.getId();
        switch(latestStateName) {
            case THEORY:
                TrainingStateTheory theory =  theoryRepository.getOne(trainingId);
                theory.incrementStateStatusCount();
                theoryRepository.save(theory); break;
            case PRACTICE:
                TrainingStatePractice practice =  practiceRepository.getOne(trainingId);
                practice.incrementStateStatusCount();
                practiceRepository.save(practice); break;
            case THEORY_TEST:
                TrainingStateTheoryTest theoryTest =  theoryTestRepository.getOne(trainingId);
                theoryTest.incrementStateStatusCount();
                theoryTestRepository.save(theoryTest); break;
            case RANGE_TEST:
                TrainingStateRangeTest rangeTest =  rangeTestRepository.getOne(trainingId);
                rangeTest.incrementStateStatusCount();
                rangeTestRepository.save(rangeTest); break;
            case CITY_TEST:
                TrainingStateCityTest cityTest =  cityTestRepository.getOne(trainingId);
                cityTest.incrementStateStatusCount();
                cityTestRepository.save(cityTest); break;
            default:
                throw new Error("No valid state name defined");
        }
    }

    @Override
    public TrainingStateTheory initializeTrainingState(Training training) {
        TrainingStateTheory initialState = new TrainingStateTheory(training);
        return theoryRepository.save(initialState);
    }

    @Override
    public void removeFromCurrentStateTable(
            Training training,
            TrainingStateName currentStateName
    ) {
        Long trainingId = training.getId();
        // smelly code, but it has to exist somewhere
        switch(currentStateName) {
            case THEORY:
                theoryRepository.deleteById(trainingId); break;
            case PRACTICE:
                practiceRepository.deleteById(trainingId); break;
            case THEORY_TEST:
                theoryTestRepository.deleteById(trainingId); break;
            case RANGE_TEST:
                rangeTestRepository.deleteById(trainingId); break;
            case CITY_TEST:
                cityTestRepository.deleteById(trainingId); break;
            default:
                throw new Error("No valid state name defined");
        }
    }

    @Override
    public TrainingState addToNextStateTable(
            Training training,
            TrainingStateName currentStateName
    ) {
        // smelly code, but it has to exist go somewhere
        switch(currentStateName) {
            case THEORY:
                TrainingStatePractice practice = new TrainingStatePractice(training);
                return practiceRepository.save(practice);
            case PRACTICE:
                TrainingStateTheoryTest theoryTest = new TrainingStateTheoryTest(training);
                return theoryTestRepository.save(theoryTest);
            case THEORY_TEST:
                TrainingStateRangeTest rangeTest = new TrainingStateRangeTest(training);
                return rangeTestRepository.save(rangeTest);
            case RANGE_TEST:
                TrainingStateCityTest cityTest = new TrainingStateCityTest(training);
                return cityTestRepository.save(cityTest);
            default:
                throw new Error("No valid state name defined");
        }
    }


}
