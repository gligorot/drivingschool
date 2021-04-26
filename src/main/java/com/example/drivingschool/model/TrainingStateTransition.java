package com.example.drivingschool.model;

import com.example.drivingschool.model.enumerations.TrainingStateName;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "training_state_transition")
public class TrainingStateTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id")
    private Training training;

    @Enumerated(EnumType.STRING)
    private TrainingStateName fromState;

    @Enumerated(EnumType.STRING)
    private TrainingStateName toState;

    @CreatedDate
    LocalDateTime timestamp; // should be private? won't change in order not to break something

    public TrainingStateTransition() {
    }

    public TrainingStateTransition(Training training, TrainingStateName fromState, TrainingStateName toState) {
        this.training = training;
        this.fromState = fromState;
        this.toState = toState;
    }
}
