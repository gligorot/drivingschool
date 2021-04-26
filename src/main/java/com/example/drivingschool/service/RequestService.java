package com.example.drivingschool.service;

import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.dto.RequestDTO;
import com.example.drivingschool.model.enumerations.TrainingStateName;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestService {

    List<Request> getAllActiveAcceptedRequestsForTraining(Training training);

    List<RequestDTO> getAllPendingRequestsForInstructor(Instructor instructor);

    List<RequestDTO> getAllActiveAcceptedRequestsForInstructor(Instructor instructor);

    Request getById(Long requestId);

    Request approve(Request request);

    Request refuse(Request request);

    Request create(Training training, Instructor instructor, TrainingStateName type, Integer currentCount, LocalDateTime time);
}
