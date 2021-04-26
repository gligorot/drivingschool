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
@Table(name = "city_test_inter_state_transition")
public class CityTestInterStateTransition implements InterStateTransition {

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

    private Integer fromTries;

    private Integer toTries;

    private LocalDateTime timestamp;

    public CityTestInterStateTransition() {
    }

    public CityTestInterStateTransition(Request request) {
        this.request = request;
        this.training = request.getTraining();
        this.fromTries = request.getFromValue();
        this.toTries = request.getToValue();
        this.timestamp = request.getTimestamp();
    }
}
