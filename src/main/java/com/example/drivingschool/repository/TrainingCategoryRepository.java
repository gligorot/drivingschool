package com.example.drivingschool.repository;

import com.example.drivingschool.model.TrainingCategory;
import com.example.drivingschool.model.TrainingStateTransition;
import com.example.drivingschool.model.enumerations.TrainingCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingCategoryRepository extends JpaRepository<TrainingCategory, Long> {

    TrainingCategory findByName(TrainingCategoryName name);

}
