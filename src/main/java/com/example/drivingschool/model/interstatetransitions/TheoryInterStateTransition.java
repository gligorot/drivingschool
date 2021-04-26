package com.example.drivingschool.model.interstatetransitions;

import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.Training;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "theory_inter_state_transition")
public class TheoryInterStateTransition implements InterStateTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Training training;

    private Integer fromDaysCompleted;

    private Integer toDaysCompleted;

    private LocalDateTime timestamp;

    public TheoryInterStateTransition() {
    }

    public TheoryInterStateTransition(Request request) {
        this.request = request;
        this.training = request.getTraining();
        this.fromDaysCompleted = request.getFromValue();
        this.toDaysCompleted = request.getToValue();
        this.timestamp = request.getTimestamp();
    }
}
