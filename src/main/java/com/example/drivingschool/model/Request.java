package com.example.drivingschool.model;

import com.example.drivingschool.model.enumerations.RequestStatus;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instructor instructor;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Enumerated(EnumType.STRING)
    // see TrainingStateName as a hypothetical RequestType enum (they would be the same)
    private TrainingStateName type;

    private Integer fromValue;

    private Integer toValue;

    private LocalDateTime timestamp;

    public Request() {
    }

    public Request(Training training, Instructor instructor, LocalDateTime timestamp, TrainingStateName requestType, Integer fromValue, Integer toValue) {
        this.training = training;
        this.instructor = instructor;
        this.status = RequestStatus.PENDING; // default value
        this.type = requestType;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.timestamp = timestamp;
    }
}
