package com.example.drivingschool.repository;

import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.Training;
import com.example.drivingschool.model.enumerations.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByInstructorId(Long instructorId);

    List<Request> findAllByInstructorIdAndStatus(Long instructorId, RequestStatus status);

    List<Request> findAllByTrainingAndStatusAndTimestampAfter(Training training, RequestStatus status, LocalDateTime timestamp);

    List<Request> findAllByInstructorIdAndStatusAndTimestampAfter(Long instructorId, RequestStatus status, LocalDateTime timestamp);

    List<Request> findAllByInstructorIdAndStatusInAndTimestampAfter(Long instructorId, List<RequestStatus> statuses, LocalDateTime timestamp);

}
