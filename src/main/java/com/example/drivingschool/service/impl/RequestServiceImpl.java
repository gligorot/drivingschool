package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.dto.RequestDTO;
import com.example.drivingschool.model.enumerations.RequestStatus;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.example.drivingschool.repository.RequestRepository;
import com.example.drivingschool.service.InterStateTransitionService;
import com.example.drivingschool.service.RequestService;
import com.example.drivingschool.service.TrainingStateService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final InterStateTransitionService istService;
    private final TrainingStateService trainingStateService;

    public RequestServiceImpl(
            RequestRepository requestRepository,
            InterStateTransitionService istService,
            TrainingStateService trainingStateService
    ) {
        this.requestRepository = requestRepository;
        this.istService = istService;
        this.trainingStateService = trainingStateService;
    }

    @Override
    @Transactional
    public List<Request> getAllActiveAcceptedRequestsForTraining(Training training) {
        List<Request>  requests = requestRepository
                .findAllByTrainingAndStatusAndTimestampAfter(
                        training,
                        RequestStatus.ACCEPTED,
                        LocalDateTime.now()
                );
        return requests;
//                .stream().map(RequestDTO::new)
//                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RequestDTO> getAllPendingRequestsForInstructor(Instructor instructor) {
        List<Request> requests = requestRepository
                .findAllByInstructorIdAndStatus(
                        instructor.getId(),
                        RequestStatus.PENDING
                );
        return requests
                .stream().map(RequestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RequestDTO> getAllActiveAcceptedRequestsForInstructor(Instructor instructor) {
        List<Request> requests = requestRepository
                .findAllByInstructorIdAndStatusAndTimestampAfter(
                        instructor.getId(),
                        RequestStatus.ACCEPTED,
                        LocalDateTime.now()
                );
        return requests
                .stream().map(RequestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Request getById(Long requestId) {
        return requestRepository.getOne(requestId);
    }

    @Override
    @Transactional
    public Request approve(Request request) {

        istService.createAndSaveInterStateTransitionFromRequest(request); // smelly

        Training training = request.getTraining();
        TrainingStateName currentState = request.getType();
        trainingStateService
                .increaseStatusCountForTrainingWithLatestState(training, currentState);

        return accept(request);
    }

    public Request accept(Request request) {

        request.setStatus(RequestStatus.ACCEPTED);
        return requestRepository.save(request);
    }

    @Override
    public Request refuse(Request request) {

        request.setStatus(RequestStatus.REFUSED);
        return requestRepository.save(request);
    }

    @Override
    public Request create(Training training, Instructor instructor, TrainingStateName type, Integer currentCount, LocalDateTime time) {

        Request request = new Request(
                training,
                instructor,
                time,
                type,
                currentCount,
                currentCount + 1
        );
        return requestRepository.save(request);
    }

}

