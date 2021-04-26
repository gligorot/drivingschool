package com.example.drivingschool.model.dto;

import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.TrainingStateTransition;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import lombok.Data;

import java.util.List;

@Data
public class TrainingDetailsDTO {
    private TrainingStateName currentState;
    private Integer currentStateCount;
    private Boolean currentStatePromotable;
    private List<Request> activeAcceptedRequests;
    private List<TrainingStateTransition> stateTransitions;

    public TrainingDetailsDTO() {
    }

    // all args (lombok is kinda wonky for this?)
    public TrainingDetailsDTO(
            TrainingStateName currentState,
            Integer currentStateCount,
            Boolean currentStatePromotable,
            List<Request> activeAcceptedRequests,
            List<TrainingStateTransition> stateTransitions
    ) {
        this.currentState = currentState;
        this.currentStateCount = currentStateCount;
        this.currentStatePromotable = currentStatePromotable;
        this.activeAcceptedRequests = activeAcceptedRequests;
        this.stateTransitions = stateTransitions;
    }
}
