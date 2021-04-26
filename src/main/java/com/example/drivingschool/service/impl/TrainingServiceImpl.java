package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.*;
import com.example.drivingschool.model.dto.TrainingDTO;
import com.example.drivingschool.model.dto.TrainingDetailsDTO;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.example.drivingschool.repository.TrainingRepository;
import com.example.drivingschool.service.RequestService;
import com.example.drivingschool.service.TrainingService;
import com.example.drivingschool.service.TrainingStateService;
import com.example.drivingschool.service.TrainingStateTransitionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingStateTransitionService transitionService;
    private final RequestService requestService;
    private final TrainingStateService trainingStateService;

    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingStateTransitionService transitionService, RequestService requestService, TrainingStateService trainingStateService) {
        this.trainingRepository = trainingRepository;
        this.transitionService = transitionService;
        this.requestService = requestService;
        this.trainingStateService = trainingStateService;
    }

    @Override
    public Training getById(Long trainingId) {
        return trainingRepository.findById(trainingId).orElseThrow();
    }

    @Override
    public List<TrainingDTO> getAllForInstructorWithDetails(Instructor instructor) {
        Long instructorId = instructor.getId();
        List<TrainingDTO> instructorTrainingDTOs = trainingRepository
                .findAllByInstructorId(instructorId)
                .stream().map(training -> {
                    TrainingDetailsDTO trainingDetailsDTO = getTrainingDetailsFrom(training);
                    return new TrainingDTO(training, trainingDetailsDTO);
                })
                .collect(Collectors.toList());
        return instructorTrainingDTOs;
    }

    private TrainingDetailsDTO getTrainingDetailsFrom(Training training) {

        List<Request> activeAcceptedRequests =
                requestService.getAllActiveAcceptedRequestsForTraining(training);

        List<TrainingStateTransition> stateTransitions =
                transitionService.findAllForTraining(training);
        TrainingStateTransition latestTransition =
                transitionService.getLatestFrom(stateTransitions);

        TrainingStateName latestStateName = latestTransition.getToState(); // smelly but can't bother now
        Integer currentStateCount = trainingStateService
                .getCountForTrainingWithLatestState(training, latestStateName);

        boolean currentStatePromotable =
                training.isPromotable(latestStateName, currentStateCount); // dirty diety code...

        return new TrainingDetailsDTO(
                latestStateName,
                currentStateCount,
                currentStatePromotable,
                activeAcceptedRequests,
                stateTransitions
        );
    }

    @Override
    public List<TrainingDTO> getAllForCandidateWithDetails(Candidate candidate) {

        Long candidateId = candidate.getId();
        List<TrainingDTO> candidateTrainingDTOs = trainingRepository
                .findAllByCandidateId(candidateId)
                .stream().map(training -> {
                    TrainingDetailsDTO trainingDetailsDTO = getTrainingDetailsFrom(training);
                    return new TrainingDTO(training, trainingDetailsDTO);
                })
                .collect(Collectors.toList());
        return candidateTrainingDTOs;
    }

    @Override
    public Training createNewTraining(Instructor instructor, TrainingCategory category, Candidate candidate) {
        Training training = new Training(instructor, category, candidate);
        Training savedTraining = trainingRepository.save(training);
        transitionService.initiateTrainingState(training);

        return savedTraining;
    }
}
