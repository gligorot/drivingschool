package com.example.drivingschool.service;


import com.example.drivingschool.model.TrainingCategory;
import com.example.drivingschool.model.enumerations.TrainingCategoryName;

import java.util.List;

// not really useful, since there's a limited number of categories they can be added manually
public interface TrainingCategoryService {

    TrainingCategory createNew(TrainingCategoryName name, Integer requiredTheoryTrainingDays, Integer requiredPracticeTrainingDays);

    TrainingCategory getByName(TrainingCategoryName name);

    List<TrainingCategory> getAll();
}
