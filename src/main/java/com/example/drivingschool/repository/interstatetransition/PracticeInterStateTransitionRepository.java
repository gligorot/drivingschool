package com.example.drivingschool.repository.interstatetransition;

import com.example.drivingschool.model.interstatetransitions.CityTestInterStateTransition;
import com.example.drivingschool.model.interstatetransitions.PracticeInterStateTransition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeInterStateTransitionRepository extends JpaRepository<PracticeInterStateTransition, Long> {
}
