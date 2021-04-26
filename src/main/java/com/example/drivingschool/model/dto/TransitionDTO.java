package com.example.drivingschool.model.dto;

import com.example.drivingschool.model.TrainingStateTransition;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransitionDTO {
    private String state; // done, active or future
    private TrainingStateName stateName;
    private LocalDateTime timestamp;

    public TransitionDTO(TrainingStateTransition transition) {
        this.state = "done";
        this.stateName = transition.getToState();
        this.timestamp = transition.getTimestamp();
    }

    public TransitionDTO(TrainingStateName stateName) {
        this.state = "future";
        this.stateName = stateName;
    }
}
