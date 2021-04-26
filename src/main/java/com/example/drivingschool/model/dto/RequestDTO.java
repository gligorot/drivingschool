package com.example.drivingschool.model.dto;

import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.User;
import com.example.drivingschool.model.enumerations.RequestStatus;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDTO {
    private Long id;
    private Long trainingId;

    private Long instructorId;
    private String instructorFullName;

    private Long candidateId;
    private String candidateFullName;

    private RequestStatus status;
    private TrainingStateName type;
    private LocalDateTime timestamp;

    public RequestDTO(Request request) {
        this.id = request.getId();
        this.trainingId = request.getTraining().getId();

        User instructor = getInstructorUserFromRequest(request);
        this.instructorId = instructor.getId();
        this.instructorFullName = getFullNameForUser(instructor);

        User candidate = getCandidateUserFromRequest(request);
        this.candidateId = candidate.getId();
        this.candidateFullName = getFullNameForUser(candidate);

        this.status = request.getStatus();
        this.type = request.getType();
        this.timestamp = request.getTimestamp();
    }

    private User getInstructorUserFromRequest(Request request) {
        return request.getTraining().getInstructor().getUser();
    }

    private User getCandidateUserFromRequest(Request request) {
        return request.getTraining().getCandidate().getUser();
    }

    private String getFullNameForUser(User user) {
        return user.getName()  + " " + user.getSurname();
    }
}
